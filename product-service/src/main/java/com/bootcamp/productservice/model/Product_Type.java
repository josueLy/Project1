package com.bootcamp.productservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Data
@Document(collection = "product_type")
public class Product_Type {
    @Id
    private String typeId;
    private String description;


    public Product_Type() {

    }
}
