package project1.dto.client;


import lombok.Data;
import lombok.NoArgsConstructor;
import project1.model.Bank_Acount;

import java.util.Date;

@Data
@NoArgsConstructor
public class TransactionDto {

    private String personnelId;

    private String accountId;

    private String businessId;

    private String type;

    private double amount;

    private Date  date ;
}
