package project1.dto.credit;

import lombok.Data;
import org.springframework.data.annotation.Id;
import project1.model.Business;

@Data
public class CreditDto {

    @Id
    private String creditId;
    private String businessId;
    private Double interestRate;

}
