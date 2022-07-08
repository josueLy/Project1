package com.bootcamp.productservice.service.interfaces;

import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.model.Bank_Account;
import project1.dto.bankAccount.BankAccountDto;
import project1.model.Bank_Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountService {

    Flux<Bank_Account> findAll();

    Mono<Bank_Account> show(String id);

    Mono<Bank_Account> save(BankAccountDto bankAccountDto);

    Mono<Bank_Account> update(BankAccountDto bankAccountDto);


    Mono<Void> delete(String id);
}
