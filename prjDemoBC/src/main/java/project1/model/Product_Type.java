package project1.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Product_Type {
    @Id
    private String typeId;
    private String savin;
    private int currentAccount;
    private String fixedTerm;
    private String personnel;
    private String bussiness;
    private String creditCard;
}
