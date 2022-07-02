package project1.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

@Data
@Document(collection = "Personnel")
public class Personnel {


    private Integer idPersonal;
    private Client idClient;
    private String passaort;
    private Bank_Acount bank_acount;

    public Personnel() {
    }


}
