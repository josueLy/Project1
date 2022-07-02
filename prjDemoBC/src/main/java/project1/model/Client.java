package project1.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Data
@Document(collection = "Client")
public class Client {


    private Integer idClient;
    private String dni;
    private String name;
    private String phoneNumber;
    private String emailAddress;

}
