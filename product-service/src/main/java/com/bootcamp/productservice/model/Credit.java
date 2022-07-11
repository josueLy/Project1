package com.bootcamp.productservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "credit")
@Data
public class Credit extends  Products {

    @Id
    private String creditId;
    private Business business;
    private Double interestRate;
    private Product_Type product_type;
}
