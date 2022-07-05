package project1.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "business_account")
@Data
public class Business_Account {

    private Business business;
    private Bank_Account account;
}
