package nttdata.bootcamp.microservicios.businessclient.documents;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "type-corporate-client")
public class TypeCorporateClient {
	@Id
	private String id;

	private String typename;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;
}
