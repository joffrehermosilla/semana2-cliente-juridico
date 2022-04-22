package nttdata.bootcamp.microservicios.businessclient.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nttdata.bootcamp.microservicios.businessclient.documents.CorporateClient;

import nttdata.bootcamp.microservicios.businessclient.services.CorporateClientService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CorporateClientController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CorporateClientController.class);
	@Autowired
	private CorporateClientService service;

	@GetMapping("/all")
	public Flux<CorporateClient> searchAll() {
		Flux<CorporateClient> corp = service.findAlls();
		LOGGER.info("CORPORATE CLIENT registered: " + corp);
		return corp;
	}

	@GetMapping("/id/{id}")
	public Mono<CorporateClient> searchById(@PathVariable String id) {
		LOGGER.info("Corporate Client id: " + service.findById(id) + " con codigo: " + id);
		return service.findById(id);
	}

	@PostMapping("/create-corporate-client")
	public Mono<CorporateClient> createCorporateClient(@Valid @RequestBody CorporateClient corporateClient) {
		LOGGER.info("CORPORATE CLIENT create: " + service.saves(corporateClient));
		return service.saves(corporateClient);
	}

}
