package com.service.clientservice.dto.client;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor

public class PersonnelDto{

    private String idPersonal;
    private String dni;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String passport;


}
