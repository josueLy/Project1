package proyecto1.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Data
@Document(collection = "Client")
public class Client {
    @Id
    private Integer idClient;
    private String dni;
    private String name;
    private String phoneNumber;
    private String emailAddress;

}
