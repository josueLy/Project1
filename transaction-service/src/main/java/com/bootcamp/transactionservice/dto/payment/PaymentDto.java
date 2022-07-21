package com.bootcamp.transactionservice.dto.payment;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentDto {

    private String businessId;
    private String personnelId;
    private Date startDate;
    private Date endDate;
}
