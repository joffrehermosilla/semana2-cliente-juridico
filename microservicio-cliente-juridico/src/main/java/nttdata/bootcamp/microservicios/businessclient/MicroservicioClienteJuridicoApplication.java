package nttdata.bootcamp.microservicios.businessclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class MicroservicioClienteJuridicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioClienteJuridicoApplication.class, args);
	}

}


//actualizacion en github actions