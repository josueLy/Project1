package project1.dto.client;

import lombok.Data;
import lombok.NoArgsConstructor;
import project1.model.Bank_Acount;
import project1.model.Client;


@Data
@NoArgsConstructor

public class PersonnelDto{


    private String dni;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String passaort;


}
