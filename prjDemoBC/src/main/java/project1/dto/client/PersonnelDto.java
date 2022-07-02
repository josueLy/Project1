package project1.dto.client;

import lombok.Data;
import lombok.NoArgsConstructor;
import project1.model.Bank_Acount;
import project1.model.Client;


@Data
@NoArgsConstructor
public class PersonnelDto extends  ClientDto{


    private String passaort;
    private Bank_Acount bank_acount;

}
