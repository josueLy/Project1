package com.bootcamp.productservice.service.interfaces;

import com.bootcamp.productservice.dto.BusinessAcount.AccountDto;
import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.model.Business_Account;
import com.bootcamp.productservice.model.Product_Type;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountService {

    Flux<Bank_Account> findAll();

    Mono<Bank_Account> show(String id);

    Mono<Bank_Account> save(BankAccountDto bankAccountDto);

    //3.
    Flux<Product_Type> listProducts(BankAccountDto bankAccountDto);

    Mono<Bank_Account> update(BankAccountDto bankAccountDto);


    Mono<Void> delete(String id);

    Flux<Bank_Account> showAccountsByClient(AccountDto accountDto);
}
