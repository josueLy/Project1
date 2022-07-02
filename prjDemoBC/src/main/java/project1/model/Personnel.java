package project1.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

@Data
public class Personnel extends  Client {

    private Integer idPersonal;
    private String passaort;
    private Bank_Acount bank_acount;

    public Personnel() {
    }

    public Personnel(Integer idClient, String dni, String name, String phoneNumber, String emailAddress) {
        super(idClient, dni, name, phoneNumber, emailAddress);
    }

}
