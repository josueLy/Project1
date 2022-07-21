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

<<<<<<< HEAD
    private Date creationDate;
=======
    private double avaliableBalance;
>>>>>>> 2d5d6896871d03f2ee8d08d3b1a3a4065f3677b7

    private int max_number_transactions;

    private Product_Type product_type;

    private String card_number;

    private boolean isPrincipal_account;



}
