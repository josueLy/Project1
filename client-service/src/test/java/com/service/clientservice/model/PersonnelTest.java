package com.service.clientservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonnelTest {
    @Test
    @DisplayName("testing with Personnel information ")
    void testClient() {
        Personnel personnel = new Personnel("0003", "87364391");

        String passaportEsperado = "87364391";
        String passaporOficial = personnel.getPassaport();

        Assertions.assertEquals(passaportEsperado,passaporOficial);

    }
}