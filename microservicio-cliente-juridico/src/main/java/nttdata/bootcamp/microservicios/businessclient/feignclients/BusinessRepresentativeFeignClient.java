package nttdata.bootcamp.microservicios.businessclient.feignclients;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import nttdata.bootcamp.microservicios.businessclient.documents.feign.BusinessRepresentative;
import reactor.core.publisher.Mono;

@FeignClient(name = "microservicio-representante-empresarial")
public interface BusinessRepresentativeFeignClient {

	@PostMapping("/create-business-representative")
	public ResponseEntity<Mono<?>> createBusinessRepresentative(
			@Valid @RequestBody BusinessRepresentative businessrepresentative);
	
	
	
	@PostMapping("/create-simple-business-repre")
	public Mono<BusinessRepresentative> createSimpleRepre(BusinessRepresentative simplerepre);

}
