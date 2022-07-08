package com.service.clientservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


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

   // private Bank_Account account;


    public Personnel() {

    }
}
