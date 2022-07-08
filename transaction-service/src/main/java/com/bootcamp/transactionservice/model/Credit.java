package com.bootcamp.transactionservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "credit")
@Data
public class Credit {

    @Id
    private String creditId;
    private Business business;
    private Double interestRate;
}
