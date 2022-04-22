package nttdata.bootcamp.microservicios.businessclient.services;

import nttdata.bootcamp.microservicios.businessclient.documents.CorporateClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CorporateClientService {

	public Mono<CorporateClient> findById(String id);

	public Flux<CorporateClient> findAlls();

	public Mono<CorporateClient> saves(CorporateClient document);

	public Mono<Void> delete(CorporateClient document);

}
