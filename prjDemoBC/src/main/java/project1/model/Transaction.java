package project1.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
@Data

public class Transaction {
    @Id
    private String transactionId;
    private Bank_Acount idAccount;

    private Personnel personnel;
    private Business  business;

    public Transaction(String transactionId, Bank_Acount idAccount, Personnel personnel, Business business) {
        this.transactionId = transactionId;
        this.idAccount = idAccount;
        this.personnel = personnel;
        this.business = business;
    }

    public Transaction() {

    }
}
