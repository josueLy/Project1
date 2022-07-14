package com.bootcamp.productservice.dto.client;

import com.bootcamp.productservice.model.Bank_Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonnelDto{

    private String idPersonal;
    private String dni;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String passport;
    private List<Bank_Account> accounts;


}
