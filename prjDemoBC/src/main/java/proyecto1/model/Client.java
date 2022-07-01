package proyecto1.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

@Data
@Entity
@Table(name="Client")
public class Client {
    @Id
    @Column(name = "id", nullable = false)
    private Integer idClient;
    private String dni;
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Client() {
    }
}
