package com.bootcamp.productservice.service.impl;

import com.bootcamp.productservice.dto.bankAccount.data.BusinessAccountDto;
import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.repository.IBankAccountRepository;
import com.bootcamp.productservice.service.interfaces.IBankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BankAccountServiceImplTest {
    @Mock
    private IBankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private Flux<Bank_Account> bankAccountFlux;
    private Bank_Account bankAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bankAccount = new Bank_Account();
        bankAccount.setAccountId("001");
        bankAccount.setNumberAccount("0984582304");
        bankAccount.setComission(500);
        bankAccount.setAvailableBalance(900.99);

        List<Bank_Account> bankAccountList = new ArrayList<>();
        bankAccountList.add(bankAccount);

        bankAccountFlux= Mono.just(bankAccountList)
                .flatMapMany(Flux::fromIterable)
                .log();

    }

    @Test
    void findAll() {
        when(bankAccountRepository.findAll()).thenReturn(bankAccountFlux);
        assertNotNull(bankAccountService.findAll());

    }
}