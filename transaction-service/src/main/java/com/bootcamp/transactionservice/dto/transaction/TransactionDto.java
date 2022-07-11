package com.bootcamp.transactionservice.dto.transaction;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionDto {

    private String transactionId;

    private String personnelId;

    private String accountId;

    private String businessId;

    private String type;

    private double amount;
    private String numeroMT;


}
