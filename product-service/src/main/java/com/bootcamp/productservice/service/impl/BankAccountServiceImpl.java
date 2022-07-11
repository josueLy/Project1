package com.bootcamp.productservice.service.impl;

import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.repository.IBankAccountRepository;
import com.bootcamp.productservice.service.interfaces.IBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountServiceImpl implements IBankAccountService {

    @Autowired
    private IBankAccountRepository bankAccountRepository;


    @Override
    public Flux<Bank_Account> findAll() {
        return bankAccountRepository.findAll();
    }

    @Override
    public Mono<Bank_Account> show(String id) {
        return bankAccountRepository.findById(id);
    }

    @Override
    public Mono<Bank_Account> save(BankAccountDto bankAccountDto) {
        Bank_Account bank_acount = new Bank_Account();

        bank_acount.setAvailableBalance(bankAccountDto.getAvailableBalance());
        bank_acount.setNumberAccount(bankAccountDto.getNumberAccount());
        bank_acount.setComission(bankAccountDto.getComission());

        return bankAccountRepository.save(bank_acount);

    }

    @Override
    public Mono<Bank_Account> update(BankAccountDto bankAccountDto) {
        //get the mono of bank account by Id
        Mono<Bank_Account> bankAcountMono = bankAccountRepository.findById(bankAccountDto.getBankAccountId());

        bankAcountMono = bankAcountMono.map(/*get the bank_Account Object from mono*/bank_account -> {
            //Set the bank account and assign the mono of bank_Account (bankAcountMono)
            bank_account.setNumberAccount(bankAccountDto.getNumberAccount());
            bank_account.setAvailableBalance(bankAccountDto.getAvailableBalance());
            bank_account.setComission(bankAccountDto.getComission());
            return bank_account;
        }).flatMap(result ->
                //save the new bank_account
                bankAccountRepository.save(result));

        return bankAcountMono;
    }

    @Override
    public Mono<Void> delete(String id) {

       return bankAccountRepository.deleteById(id);
    }
}
