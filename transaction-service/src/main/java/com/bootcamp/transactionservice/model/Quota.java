package com.bootcamp.transactionservice.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "quota")
public class Quota {

    private String quotaId;
    private double price;
    private Date expirationDate;

}
