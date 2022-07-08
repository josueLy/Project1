package com.bootcamp.productservice.dto.product_type;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product_TypeDto {

    private String saving;
    private String currentAccount;
    private String fixedTerm;
    private String personnel;
    private String bussiness;
    private String creditCard;
}
