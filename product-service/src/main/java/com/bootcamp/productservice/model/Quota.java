package com.bootcamp.productservice.model;

import lombok.Data;
<<<<<<< HEAD
=======
import org.springframework.data.annotation.Id;
>>>>>>> fbc559c525c526a38f4f86e8a8b3c66798da4735
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "quota")
public class Quota {

<<<<<<< HEAD
=======
    @Id
>>>>>>> fbc559c525c526a38f4f86e8a8b3c66798da4735
    private String quotaId;
    private double price;
    private Date expirationDate;

}
