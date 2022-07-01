package proyecto1.model;

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
@Table(name="Personnel")
@Entity
public class Personnel extends  Client {

    private Integer idPersonal;
    private String passaort;

    @OneToOne(mappedBy = "Bank_Account")
    private Bank_Acount bank_acount;

    public Personnel() {
    }

    public Personnel(Integer idClient, String dni, String name, String phoneNumber, String emailAddress) {
        super(idClient, dni, name, phoneNumber, emailAddress);
    }

}
