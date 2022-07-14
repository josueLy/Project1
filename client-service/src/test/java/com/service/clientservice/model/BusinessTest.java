package com.service.clientservice.model;

import com.service.clientservice.dto.client.BusinessDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessTest {
    @Test
    @DisplayName("testing with Bussines information ")

    void testBussines(){
        Business business = new Business("001","1755095767");


        String rucEsperado = "1755095767";
        String rucOficial = business.getRuc();

        Assertions.assertEquals(rucOficial,rucEsperado);

    }

}