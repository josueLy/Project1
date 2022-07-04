package project1.service.interfaces;

import project1.dto.bankAccount.BankAccountDto;
import project1.model.Bank_Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountService {

    Flux<Bank_Account> findAll();

    Mono<Bank_Account> save(BankAccountDto bankAccountDto);

    Mono<Bank_Account> update(BankAccountDto bankAccountDto);


}
