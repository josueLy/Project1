package project1.dto.client;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor

public class PersonnelDto{


    private String dni;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String passport;
    private String bank_account_id;



}
