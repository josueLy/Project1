package com.service.clientservice.model;

import org.apache.logging.log4j.message.StringFormattedMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonnelTest {

    @Test
    @DisplayName("testing with customer information ")
    void testClient(){
        Client client = new Client("75709845","Rolando","920349875","rjsb@gmail.com");

        String correoEsperado = "rjsb@gmail.com";
        String correoOficial = client.getEmailAddress();

        Assertions.assertEquals(correoEsperado,correoOficial);
    }

}