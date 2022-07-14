package com.bootcamp.transactionservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    @Test
    @DisplayName("testing with transaction information ")
    void testTransaction(){
        Transaction transaction = new Transaction("01","fixedTerm","9873649345",new Date(14/07/2022));

        String cuentaDestinoEsperada = "9873649345";
        String cuentaDestionOficial = transaction.getAccount_destiny();

        Assertions.assertEquals(cuentaDestinoEsperada,cuentaDestionOficial);

    }

}