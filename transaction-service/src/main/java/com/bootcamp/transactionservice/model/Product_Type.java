package com.bootcamp.transactionservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Data
public class Product_Type {
    @Id
    private String typeId;
    private String description;


    public Product_Type() {

    }
}
