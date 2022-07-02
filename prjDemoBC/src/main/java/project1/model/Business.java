package project1.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode

@Data
@Entity
@Table(name="Business")
public class Business extends Client {
    @Id
    @Column(name = "idBusiness", nullable = false)
    private Integer idBusiness;

    private String ruc;


    public Business(Integer idClient, String dni, String name, String phoneNumber, String emailAddress) {
        super(idClient, dni, name, phoneNumber, emailAddress);
    }
}
