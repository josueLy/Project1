package com.service.clientservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;


@Getter
@Setter
@ToString
@EqualsAndHashCode

@Data
@Document(collection = "personnel")
@RedisHash("Personnel")
public class Personnel extends Client implements  Serealizable {

    @Id
    private String idPersonal;
    private String passaport;
    private List<Bank_Account> accounts;
    public Personnel() {

    }
    public Personnel(String idPersonal, String passaport) {
        this.idPersonal = idPersonal;
        this.passaport = passaport;
    }

}
