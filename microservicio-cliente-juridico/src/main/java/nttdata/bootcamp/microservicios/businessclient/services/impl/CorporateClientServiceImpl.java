package nttdata.bootcamp.microservicios.businessclient.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.microservicios.businessclient.documents.CorporateClient;
import nttdata.bootcamp.microservicios.businessclient.repository.CorporateClientRepository;
import nttdata.bootcamp.microservicios.businessclient.services.CorporateClientService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CorporateClientServiceImpl implements CorporateClientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CorporateClientServiceImpl.class);

	@Autowired
	CorporateClientRepository repository;

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

}
