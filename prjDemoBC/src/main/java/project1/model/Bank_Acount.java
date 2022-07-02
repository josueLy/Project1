package project1.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Bank_Acount {

    private int idAccount;
    private Personnel personnel;

}
