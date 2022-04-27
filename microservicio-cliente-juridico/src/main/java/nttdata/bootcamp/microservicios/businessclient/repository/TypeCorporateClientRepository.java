package nttdata.bootcamp.microservicios.businessclient.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import nttdata.bootcamp.microservicios.businessclient.documents.TypeCorporateClient;

@Repository
public interface TypeCorporateClientRepository extends ReactiveMongoRepository<TypeCorporateClient, String> {

}
