package project1.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
@Data

public class Transaction {
    @Id
    private String idTransaction;
    private String idAccount;
}
