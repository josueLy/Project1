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
<<<<<<< HEAD
    private Date startDate;
    private Date endDate;
=======
    private String card_number;
    private int max_number_transactions;
>>>>>>> 2d5d6896871d03f2ee8d08d3b1a3a4065f3677b7

}
