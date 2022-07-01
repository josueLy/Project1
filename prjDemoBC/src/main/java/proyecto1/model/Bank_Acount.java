package proyecto1.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="Bank_Account")
@Data
public class Bank_Acount {
    @Id
    @Column(name = "idAccount", nullable = false)
    private Long idAccount;

    @OneToOne(mappedBy="Personnel")
    private Personnel personnel;

}
