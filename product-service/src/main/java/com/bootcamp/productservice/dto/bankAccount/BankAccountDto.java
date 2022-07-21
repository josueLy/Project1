package com.bootcamp.productservice.dto.bankAccount;

import com.bootcamp.productservice.model.Products;
import lombok.Data;

import java.util.Date;

@Data
public class BankAccountDto  {


    private String bankAccountId;
    private String businessId;
    private String personnelId;
    private String productTypeId;
    private double availableBalance;
    private String numberAccount;
    private int comission;
    private Date startDate;
    private Date endDate;

    private int moountMinA;
}
