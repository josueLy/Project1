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

}
