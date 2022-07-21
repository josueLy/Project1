package com.bootcamp.transactionservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "quota")
public class Quota {

    @Id
    private String quotaId;
    private double price;
    private Date expirationDate;
    private boolean status ;

}
