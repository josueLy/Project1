package com.bootcamp.productservice.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "bank_account")
public class Bank_Account extends  Products {

    @Id
    private String accountId;

    private int comission;

    private Date creationDate;

    //private int moountMinA;

    private Product_Type product_type;



}
