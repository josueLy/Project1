package com.service.clientservice.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "business")
@Data
@ToString
public class Business extends Client{
    @Id
    private String businessId;
    private String ruc;


    public Business(String businessId, String ruc) {
        this.businessId = businessId;
        this.ruc = ruc;
    }

    public Business() {

    }

}

