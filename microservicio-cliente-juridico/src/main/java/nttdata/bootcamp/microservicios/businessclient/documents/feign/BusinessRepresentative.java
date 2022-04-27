package nttdata.bootcamp.microservicios.businessclient.documents.feign;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRepresentative {
	private String name;

	private String dni;


	private Boolean isAccountholder;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;

	private String corporateClientId;
	
	private String status;

}
