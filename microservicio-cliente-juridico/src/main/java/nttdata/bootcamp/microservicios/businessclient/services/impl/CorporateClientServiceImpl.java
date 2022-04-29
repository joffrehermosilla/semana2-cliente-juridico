package nttdata.bootcamp.microservicios.businessclient.services.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import nttdata.bootcamp.microservicios.businessclient.documents.CorporateClient;
import nttdata.bootcamp.microservicios.businessclient.documents.feign.BusinessRepresentative;
import nttdata.bootcamp.microservicios.businessclient.feignclients.BusinessRepresentativeFeignClient;
import nttdata.bootcamp.microservicios.businessclient.repository.CorporateClientRepository;
import nttdata.bootcamp.microservicios.businessclient.services.CorporateClientService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CorporateClientServiceImpl implements CorporateClientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CorporateClientServiceImpl.class);

	@Autowired
	CorporateClientRepository repository;

	@Autowired
	RestTemplate restTemplate;

	@Value("${webclient.representante.conexion}")
	private String rutaconfig;

	@Autowired
	BusinessRepresentativeFeignClient businessFeignRepository;

	@Override
	public Mono<CorporateClient> findById(String id) {

		return repository.findById(id);
	}

	@Override
	public Flux<CorporateClient> findAlls() {

		return repository.findAll();
	}

	@Override
	public Mono<CorporateClient> saves(CorporateClient document) {

		return repository.save(document);
	}

	@Override
	public Mono<Void> delete(CorporateClient document) {

		return repository.delete(document);
	}

	@Override
	public ResponseEntity<Mono<?>> feignsaves(BusinessRepresentative businessrepresentative, String corporateClientId) {

		businessrepresentative.setCorporateClientId(corporateClientId);

		return businessFeignRepository.createBusinessRepresentative(businessrepresentative);
	}

	@Override
	public Mono<BusinessRepresentative> feignsave(BusinessRepresentative business, String id) {
		business.setCorporateClientId(id);
		return businessFeignRepository.createSimpleRepre(business);
	}

	@Override
	public Flux<BusinessRepresentative> getBusinsessRepresentative(String corporateClientId) {

		/*
		 * Flux<BusinessRepresentative> businessRepresentative =
		 * restTemplate.getForObject(
		 * "localhost:8090/api/cliente/juridico/corporate-client/" + corporateClientId,
		 * businessRepresentative);
		 */

		// WebClient client = WebClient.create();
		WebClient client = WebClient.create(rutaconfig);

		/*
		 * client = WebClient.builder() .baseUrl("http://localhost:8080")
		 * .defaultCookie("cookieKey", "cookieValue")
		 * .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		 * .defaultUriVariables(Collections.singletonMap("url",
		 * "http://localhost:8080")) .build();
		 */

		Flux<BusinessRepresentative> employeeFlux = client.get()
				.uri("/corporate-client/{corporateClientId}", corporateClientId)
				.retrieve()
				.bodyToFlux(BusinessRepresentative.class);

		employeeFlux.subscribe(x -> LOGGER.info("conexion a microservicio representante empresarial" + x));

		return employeeFlux;
	}

}
