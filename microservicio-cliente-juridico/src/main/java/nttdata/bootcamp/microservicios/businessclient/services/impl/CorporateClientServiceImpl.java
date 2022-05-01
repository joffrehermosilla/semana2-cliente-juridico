package nttdata.bootcamp.microservicios.businessclient.services.impl;

import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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

	WebClient client = WebClient.create(rutaconfig);

	private Mono<BusinessRepresentative> getBussinesRepresentativeByCorparateIdMono(
			List<BusinessRepresentative> listrepre, String corporateId) {
		Mono<BusinessRepresentative> repre =
				client
				.get()
				.uri("corporate-client/{corporateClientId}", corporateId)
				.retrieve()
				.bodyToMono(BusinessRepresentative.class);
		return repre;
	}

	private Flux<BusinessRepresentative> getBussinesRepresentativeByCorparateIdflux(
			List<BusinessRepresentative> listrepre, String corporateId) {
		BusinessRepresentative repre = new BusinessRepresentative();
		client
		.get()
		.uri("corporate-client/{corporateClientId}", corporateId)
		.retrieve()
				
		.bodyToFlux(BusinessRepresentative.class).subscribe(listrepre::add);

		return Flux.just(repre);
	}

	@Override
	public Flux<BusinessRepresentative> getBusinsessRepresentative(String corporateClientId) {

		List<BusinessRepresentative> business = new ArrayList<>();

		Flux<BusinessRepresentative> employeeFlux =

				Flux.fromIterable(business).flatMap(x -> {
					return this.getBussinesRepresentativeByCorparateIdMono(business, corporateClientId);
					// return this.getBussinesRepresentativeByCorparateIdflux(business,
					// corporateClientId);
				});

		return employeeFlux;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

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

}
