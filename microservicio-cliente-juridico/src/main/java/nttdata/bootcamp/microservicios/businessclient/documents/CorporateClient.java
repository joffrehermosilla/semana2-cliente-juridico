package nttdata.bootcamp.microservicios.businessclient.documents;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Document(collection = "corporate-client")
public class CorporateClient {
	@Id
	private String id;

	private String corporatename;

	private String ruc;

	private String businessrepresentId;

	private String typeclientId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;

}
