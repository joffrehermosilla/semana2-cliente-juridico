package nttdata.bootcamp.microservicios.businessclient.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.microservicios.businessclient.documents.CorporateClient;
import nttdata.bootcamp.microservicios.businessclient.documents.TypeCorporateClient;
import nttdata.bootcamp.microservicios.businessclient.repository.CorporateClientRepository;
import nttdata.bootcamp.microservicios.businessclient.repository.TypeCorporateClientRepository;
import nttdata.bootcamp.microservicios.businessclient.services.TypeCorporateClientService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class TypeCorporateClientServiceImpl implements TypeCorporateClientService {

	@Autowired
	TypeCorporateClientRepository typeCorporaterepository;

	@Autowired
	CorporateClientRepository corporateRepository;

	@Override
	public Mono<TypeCorporateClient> findById(String id) {

		return typeCorporaterepository.findById(id);
	}

	@Override
	public Flux<TypeCorporateClient> findAlls() {

		return typeCorporaterepository.findAll();
	}

	@Override
	public Mono<TypeCorporateClient> saves(TypeCorporateClient document) {
	
		return typeCorporaterepository.save(document);
	}

	@Override
	public Mono<Void> delete(TypeCorporateClient document) {
		// TODO Auto-generated method stub
		return typeCorporaterepository.delete(document);
	}

	@Override
	public Flux<CorporateClient> findbyName(String typenaturalId) {
		// TODO Auto-generated method stub
		return null;
	}

}
