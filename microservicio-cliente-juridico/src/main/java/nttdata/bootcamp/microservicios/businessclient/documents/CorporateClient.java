package nttdata.bootcamp.microservicios.businessclient.documents;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "corporate-client")
public class CorporateClient {
	@Id
	private String id;

	private String corporatename;

	private String ruc;

	private String clientStatus;

//	private String businessrepresentId;

	private List<TypeCorporateClient> listtypeCorporate;

	private TypeCorporateClient type;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;

}
