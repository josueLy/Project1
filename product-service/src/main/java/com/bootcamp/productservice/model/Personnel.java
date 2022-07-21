package com.bootcamp.productservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Getter
@Setter
@ToString
@EqualsAndHashCode

@Data

@Document(collection = "personnel")
public class Personnel extends Client{

    @Id
    private String idPersonal;

    private String passaport;

    private List<Bank_Account> accounts;

    // private Date creationDate


    public Personnel() {

    }
}
