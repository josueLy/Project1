package com.bootcamp.productservice.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PaymentDto {


    private String personnelId;
    private String businessId;
    private Date startDate;
    private Date endDate;





}
