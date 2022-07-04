package project1.dto.client;


import lombok.Data;
import lombok.NoArgsConstructor;
import project1.model.Bank_Acount;

@Data
@NoArgsConstructor
public class TransactionDto {

    private String commission;

    private String idPersonal;

    private String accountId;

    private String businessId;
}
