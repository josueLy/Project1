package com.service.clientservice.model;

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
