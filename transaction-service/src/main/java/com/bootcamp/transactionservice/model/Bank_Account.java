package com.bootcamp.transactionservice.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "bank_account")
public class Bank_Account extends  Products {

    @Id
    private String accountId;

    private int comission;

    private int max_number_transactions;

    private Product_Type product_type;

    private String card_number;

    private boolean isPrincipal_account;


}
