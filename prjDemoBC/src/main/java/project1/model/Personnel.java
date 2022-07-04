package project1.model;

import lombok.*;
import org.springframework.data.annotation.Id;


@Getter
@Setter
@ToString
@EqualsAndHashCode

@Data


public class Personnel extends Client{

    @Id
    private String idPersonal;

    private String passaport;

    private Bank_Account account;


    public Personnel() {

    }
}
