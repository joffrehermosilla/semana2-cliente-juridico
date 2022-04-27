package nttdata.bootcamp.microservicios.businessclient.services;

import org.springframework.http.ResponseEntity;

import nttdata.bootcamp.microservicios.businessclient.documents.CorporateClient;
import nttdata.bootcamp.microservicios.businessclient.documents.feign.BusinessRepresentative;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CorporateClientService {

	public Mono<CorporateClient> findById(String id);

	public Flux<CorporateClient> findAlls();

	public Mono<CorporateClient> saves(CorporateClient document);

	public Mono<Void> delete(CorporateClient document);

	public Mono<BusinessRepresentative> feignsave(BusinessRepresentative business, String id);

	public ResponseEntity<Mono<?>> feignsaves(BusinessRepresentative businessrepresentative, String corporateClientId);
}
