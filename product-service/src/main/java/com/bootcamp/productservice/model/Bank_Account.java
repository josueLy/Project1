package com.bootcamp.productservice.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "bank_account")
public class Bank_Account extends  Products {

    @Id
    private String accountId;

    private int comission;


    //private int moountMinA;

    private Product_Type product_type;



}
