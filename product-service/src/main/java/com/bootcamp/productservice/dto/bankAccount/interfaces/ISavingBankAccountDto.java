package com.bootcamp.productservice.dto.bankAccount.interfaces;

import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.model.Bank_Account;
import reactor.core.publisher.Mono;

public interface ISavingBankAccountDto {

    Mono<Bank_Account> save(Bank_Account bank_account,BankAccountDto bankAccountDto);

    Mono<Bank_Account> update(Bank_Account bank_account,BankAccountDto bankAccountDto);
}
