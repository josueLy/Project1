package com.bootcamp.transactionservice.model;

import com.bootcamp.transactionservice.dto.businessAccount.BusinessAccountDto;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "payment")
public class Payment {

    @Id
    private  String paymentId;

    private Bank_Account bank_account;

    private Quota quota;

    private Business_Account business_account;

    private Personnel personnel;


}
