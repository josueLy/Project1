package com.bootcamp.monederoMovilService.dto.monedero;

import lombok.Data;

@Data
public class MonederoDto {

    private String dni;
    private String paymentType;
    private double amount;


}
