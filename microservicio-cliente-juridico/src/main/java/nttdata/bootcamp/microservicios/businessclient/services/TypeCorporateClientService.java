package nttdata.bootcamp.microservicios.businessclient.services;

import nttdata.bootcamp.microservicios.businessclient.documents.CorporateClient;
import nttdata.bootcamp.microservicios.businessclient.documents.TypeCorporateClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TypeCorporateClientService {
	public Mono<TypeCorporateClient> findById(String id);

	public Flux<TypeCorporateClient> findAlls();

	public Mono<TypeCorporateClient> saves(TypeCorporateClient document);

	public Mono<Void> delete(TypeCorporateClient document);

	public Flux<CorporateClient> findbyName(String typenaturalId);
}
