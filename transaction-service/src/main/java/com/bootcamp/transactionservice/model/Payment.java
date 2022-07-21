package com.bootcamp.transactionservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "payment")
public class Payment {

    @Id
    private  String paymentId;

    private Bank_Account bankAccount;

    private List<Quota> quotas;

    private Business business;

    private Personnel personnel;

    private Date paymentDate;


}
