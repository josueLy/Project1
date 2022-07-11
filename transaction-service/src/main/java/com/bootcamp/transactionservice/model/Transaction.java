package com.bootcamp.transactionservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "transaction")
public class Transaction {
    @Id
    private String transactionId;

    private Bank_Account account;

    private Personnel personnel;
    private Business  business;

    private String type;
    private double amount;
    private Date date ;
    private String numeroMT;


    public Transaction() {

    }

}
