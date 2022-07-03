package project1.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

@Data
public class Personnel {

    @Id
    private String idPersonal;
    private Client idClient;
    private String passaort;
    private Bank_Acount bank_acount;

    public Personnel() {
    }


}
