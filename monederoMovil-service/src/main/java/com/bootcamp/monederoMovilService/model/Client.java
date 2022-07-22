package com.bootcamp.monederoMovilService.model;

import lombok.Data;



@Data
public class Client {

    private String dni;
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Client(String dni, String name, String phoneNumber, String emailAddress) {
        this.dni=dni;
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.emailAddress=emailAddress;
    }

    public Client() {
    }
}
