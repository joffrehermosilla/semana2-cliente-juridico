package nttdata.bootcamp.microservicios.businessclient.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import nttdata.bootcamp.microservicios.businessclient.documents.CorporateClient;

@Repository
public interface CorporateClientRepository extends ReactiveMongoRepository<CorporateClient, String> {

}
