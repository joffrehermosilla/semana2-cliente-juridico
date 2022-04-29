package nttdata.bootcamp.microservicios.businessclient.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nttdata.bootcamp.microservicios.businessclient.documents.CorporateClient;
import nttdata.bootcamp.microservicios.businessclient.documents.TypeCorporateClient;
import nttdata.bootcamp.microservicios.businessclient.documents.feign.BusinessRepresentative;
import nttdata.bootcamp.microservicios.businessclient.services.CorporateClientService;
import nttdata.bootcamp.microservicios.businessclient.services.TypeCorporateClientService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CorporateClientController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CorporateClientController.class);
	@Autowired
	private CorporateClientService service;

	@Autowired
	private TypeCorporateClientService typeservice;

	@Value("${config.balanceador.test}")
	private String balanceadorTest;

	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("balanceador", balanceadorTest);
		response.put("corporate", service.findAlls());
		return ResponseEntity.ok(response);

	}

	@GetMapping("/all")
	public Flux<CorporateClient> searchAll() {
		Flux<CorporateClient> corp = service.findAlls();
		LOGGER.info("CORPORATE CLIENT registered: " + corp);
		return corp;
	}

	@GetMapping("/type-all")
	public Flux<TypeCorporateClient> searchAllTypes() {
		Flux<TypeCorporateClient> type = typeservice.findAlls();
		LOGGER.info("CORPORATE LEGAL CLIENT registered: " + type);
		return type;
	}

	@GetMapping("/id/{id}")
	public Mono<CorporateClient> searchById(@PathVariable String id) {
		LOGGER.info("Corporate Client id: " + service.findById(id) + " con codigo: " + id);
		return service.findById(id);
	}

	@GetMapping("/corporate-representatives/{id}")
	public Flux<BusinessRepresentative> searchAllRepresentativesfromCompany(@PathVariable String id) {
		LOGGER.info(
				"BUSINESS REPRESENTATIVE find by Corporate Client id: " + service.findById(id) + " con codigo: " + id);
		return service.getBusinsessRepresentative(id);
	}

	@GetMapping("/corporate-representatives-test/{id}")
	public Flux<BusinessRepresentative> searchAllRepretest(@PathVariable String id) {
		BusinessRepresentative busyx = new BusinessRepresentative();
		List<BusinessRepresentative> bussinesslist = new ArrayList<>();
		busyx.setCorporateClientId(id);

		WebClient client = WebClient.create();

		Mono.just(bussinesslist).doOnNext(x -> {
			x.add(busyx);
		}).onErrorReturn(bussinesslist).onErrorResume(e -> Mono.just(bussinesslist))
				.onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> LOGGER.info(x.toString()));

		Flux<BusinessRepresentative> representatives = Flux.fromIterable(bussinesslist).flatMapSequential(z -> {
			Mono<BusinessRepresentative> representante = client.get()
					.uri("localhost:8090/api/representante/empresarial//corporate-client/{corporateClientId}", z)
					.retrieve().bodyToMono(BusinessRepresentative.class);
			return representante;
		});

		return representatives;
	}

	@PostMapping("/create-corporate-client")
	public Mono<CorporateClient> createCorporateClient(@Valid @RequestBody CorporateClient corporateClient) {
		LOGGER.info("CORPORATE CLIENT create: " + service.saves(corporateClient));
		return service.saves(corporateClient);
	}

	@PostMapping("/create-corporate-client/{id}")
	public ResponseEntity<Mono<?>> newCorporateClient(@PathVariable String id,
			@Valid @RequestBody CorporateClient corporates) {

		TypeCorporateClient typex = new TypeCorporateClient();

		List<TypeCorporateClient> types = new ArrayList<>();

		if (id.equals("6266e93b92137067a5ac266b")) {
			typex.setId(id);
			typex.setTypename("PyME");
			typex.setCreateAt(new Date());
		} else {
			typex.setId(id);
			typex.setTypename("average");
			typex.setCreateAt(new Date());
		}

		Mono.just(corporates).doOnNext(t -> {
			// t.setTypeNaturalclient(fx);
			types.add(typex);
			t.setListtypeCorporate(types);
			t.setType(typex);

			t.setCreateAt(new Date());

		}).onErrorReturn(corporates).onErrorResume(e -> Mono.just(corporates))
				.onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> LOGGER.info(x.toString()));

		Mono<CorporateClient> newNaturalPerson = service.saves(corporates);

		if (newNaturalPerson != null) {

			return new ResponseEntity<>(newNaturalPerson, HttpStatus.CREATED);
		}

		return new ResponseEntity<>(Mono.just(new CorporateClient()), HttpStatus.I_AM_A_TEAPOT);
	}

	@PostMapping("/create-type-corporate")
	public Mono<TypeCorporateClient> createTypeCorporateClient(
			@Valid @RequestBody TypeCorporateClient typeCorporateCLient) {
		LOGGER.info("TYPE CORPORATE CLIENT create: " + typeservice.saves(typeCorporateCLient));
		typeCorporateCLient.setCreateAt(new Date());

		return typeservice.saves(typeCorporateCLient);
	}

	@PutMapping("/update-type-corporate/{id}")
	public ResponseEntity<Mono<?>> updateTypeCorporateClient(@Valid @RequestBody TypeCorporateClient type,
			@PathVariable String id) {

		Mono<TypeCorporateClient> newTypeCorporateClient = typeservice.findById(id);
		List<TypeCorporateClient> types = new ArrayList<>();

		String lasttypename = type.getTypename();

		Mono.just(type).doOnNext(t -> {
			type.setId(id);

			t.setCreateAt(new Date());

		}).onErrorReturn(type).onErrorResume(e -> Mono.just(type))
				.onErrorMap(f -> new InterruptedException(f.getMessage()))
				.subscribe(x -> LOGGER.info("el valor cambiado es: " + x.toString()));

		newTypeCorporateClient = typeservice.saves(type);

		// NaturalLegalClient naturalx = new NaturalLegalClient();
		List<CorporateClient> corporates = new ArrayList<>();
		Flux<CorporateClient> fluxcorporate = Flux.fromIterable(corporates);
		Flux<CorporateClient> fx2 = fluxcorporate;

		fx2.filter(f -> f.getType().getTypename().equals(lasttypename)).flatMap(n -> {
			types.add(type);
			n.setListtypeCorporate(types);
			n.setType(type);
			service.saves(n);
			return Mono.just(n);
		}).collectList().onErrorReturn(corporates).onErrorResume(e -> Mono.just(corporates))
				.onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> LOGGER.info(x.toString()));

		if (newTypeCorporateClient != null) {

			return new ResponseEntity<>(newTypeCorporateClient, HttpStatus.CREATED);

		}

		return new ResponseEntity<>(Mono.just(new CorporateClient()), HttpStatus.I_AM_A_TEAPOT);
	}

	@PutMapping("{corporateClientId}/update-corporate-client/{typeCorporateId}")
	public ResponseEntity<Mono<?>> updateCorporateClient(@Valid @RequestBody CorporateClient corporates,
			@PathVariable String corporateClientId, @PathVariable String typeCorporateId) {

		// definimos tipo de cliente

		TypeCorporateClient typex = new TypeCorporateClient();

		List<TypeCorporateClient> types = new ArrayList<>();

		if (typeCorporateId.equals("6266e93b92137067a5ac266b")) {
			typex.setId(typeCorporateId);
			typex.setTypename("PyME");
			typex.setCreateAt(new Date());
		} else {
			typex.setId(typeCorporateId);
			typex.setTypename("average");
			typex.setCreateAt(new Date());
		}

		// definos cliente corporativo
		Mono<CorporateClient> newCorporateClient = service.findById(corporateClientId);

		Mono.just(corporates).doOnNext(t -> {
			corporates.setId(corporateClientId);
			types.add(typex);
			t.setId(corporateClientId);

			t.setListtypeCorporate(types);

			t.setType(typex);

			t.setCreateAt(new Date());

		}).onErrorReturn(corporates).onErrorResume(e -> Mono.just(corporates))
				.onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> LOGGER.info(x.toString()));

		newCorporateClient = service.saves(corporates);

		if (newCorporateClient != null) {

			return new ResponseEntity<>(newCorporateClient, HttpStatus.CREATED);
		}

		return new ResponseEntity<>(Mono.just(new CorporateClient()), HttpStatus.I_AM_A_TEAPOT);
	}

	@DeleteMapping("/eliminar-cliente-corporativo/{id}")
	public ResponseEntity<Mono<Void>> deleteCorporateClient(@PathVariable String id) {
		CorporateClient corporate = new CorporateClient();
		corporate.setId(id);
		Mono<CorporateClient> newCorporateClient = service.findById(id);
		newCorporateClient.subscribe();
		Mono<Void> test = service.delete(corporate);
		test.subscribe();
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/eliminar-tipocliente-corporativo/{id}")
	public ResponseEntity<Mono<Void>> deleteTypeCorporateClient(@PathVariable String id) {
		TypeCorporateClient typecorporate = new TypeCorporateClient();
		typecorporate.setId(id);
		Mono<TypeCorporateClient> newTypeNaturalPerson = typeservice.findById(id);
		newTypeNaturalPerson.subscribe();
		Mono<Void> test = typeservice.delete(typecorporate);

		test.subscribe();

		return ResponseEntity.noContent().build();
	}

	protected ResponseEntity<Mono<?>> validar(BindingResult result) {
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});

		ResponseEntity.badRequest().body(errores);

		return new ResponseEntity<>(Mono.just(errores), HttpStatus.I_AM_A_TEAPOT);
		// return ResponseEntity.badRequest().body(errores);
	}

	// bloqueando con feign client
	@PostMapping("/create-business-representative/{id}")
	public ResponseEntity<Mono<?>> saveBusinessRepresentative(@Valid @RequestBody BusinessRepresentative business,
			@PathVariable String id) {
		business.setCorporateClientId(id);

		Mono.just(business).doOnNext(t -> {
			// business.setStatus("firmante autorizado");
			business.setStatus("TITULAR");
			business.setCreateAt(new Date());
		}).onErrorReturn(business).onErrorResume(e -> Mono.just(business))
				.onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> LOGGER.info(x.toString()));
		// INTENTO DE DISCRIMINAR SI YA EXISTE EL REPRESENTANTE COMO TITULAR SI NO ES
		// ASI SE LE ASIGNA

		Mono<BusinessRepresentative> newBusinessReperesentative = service.feignsave(business, id);

		if (newBusinessReperesentative != null) {
			business.setStatus("TITULAR");
			business.setCreateAt(new Date());
			return new ResponseEntity<>(newBusinessReperesentative, HttpStatus.CREATED);

		}

		return new ResponseEntity<>(Mono.just(new BusinessRepresentative()), HttpStatus.I_AM_A_TEAPOT);

	}

	// bloqueando con feign Client
	@PostMapping("/create-business-simple-representative/{id}")
	public ResponseEntity<Mono<?>> saveSimpleRepresentative(@Valid @RequestBody BusinessRepresentative business,
			@PathVariable String id) {
		BusinessRepresentative businessx = service.feignsave(business, id).block();
		businessx.setCorporateClientId(id);
		// business.setStatus("firmante autorizado");
		businessx.setStatus("TITULAR");
		businessx.setCreateAt(new Date());
		business = businessx;
		Mono<BusinessRepresentative> newBusinessReperesentative = service.feignsave(businessx, id);

		if (newBusinessReperesentative != null) {

			return new ResponseEntity<>(newBusinessReperesentative, HttpStatus.CREATED);

		}

		return new ResponseEntity<>(Mono.just(new BusinessRepresentative()), HttpStatus.I_AM_A_TEAPOT);
	}

}
