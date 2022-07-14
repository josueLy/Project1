package com.service.clientservice.dto.client;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusinessDto{

    private String businessId;
    private String dni;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String ruc;

    public BusinessDto(String businessId, String dni, String name, String phoneNumber, String emailAddress, String ruc) {
        this.businessId = businessId;
        this.dni = dni;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.ruc = ruc;
    }
}
