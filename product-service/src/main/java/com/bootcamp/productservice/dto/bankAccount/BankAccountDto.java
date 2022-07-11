package com.bootcamp.productservice.dto.bankAccount;

import com.bootcamp.productservice.model.Products;
import lombok.Data;

@Data
public class BankAccountDto  {


    private String bankAccountId;
    private String productTypeId;
    private double availableBalance;
    private String numberAccount;
    private int comission;

}
