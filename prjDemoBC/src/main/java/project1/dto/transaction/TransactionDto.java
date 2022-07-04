package project1.dto.transaction;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TransactionDto {

    private String personnelId;

    private String accountId;

    private String businessId;

    private String type;

    private double amount;


}
