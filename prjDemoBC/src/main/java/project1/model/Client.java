package project1.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

@Data

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
