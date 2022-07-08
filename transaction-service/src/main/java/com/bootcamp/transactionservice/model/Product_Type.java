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
    private String saving;
    private String currentAccount;
    private String fixedTerm;
    private String personnel;
    private String bussiness;
    private String creditCard;

    public Product_Type(String typeId, String saving, String currentAccount, String fixedTerm, String personnel, String bussiness, String creditCard) {
        this.typeId = typeId;
        this.saving = saving;
        this.currentAccount = currentAccount;
        this.fixedTerm = fixedTerm;
        this.personnel = personnel;
        this.bussiness = bussiness;
        this.creditCard = creditCard;
    }

    public Product_Type() {

    }
}
