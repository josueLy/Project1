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

    private Bank_Account bank_acount;

    public Personnel(String idPersonal, String passaport, Bank_Account bank_acount) {
        this.idPersonal = idPersonal;
        this.passaport = passaport;
        this.bank_acount = bank_acount;
    }

    public Personnel() {

    }
}
