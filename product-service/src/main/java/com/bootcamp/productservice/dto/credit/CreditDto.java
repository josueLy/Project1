package com.bootcamp.productservice.dto.credit;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class CreditDto {

    @Id
    private String creditId;
    private String businessId;
    private Double interestRate;

}
