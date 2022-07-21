package com.bootcamp.productservice.model;

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

    private Bank_Account bank_account;

    private List<Quota> quotas;

    private Business_Account business_account;

    private Personnel personnel;

    private Date payment_date;

}
