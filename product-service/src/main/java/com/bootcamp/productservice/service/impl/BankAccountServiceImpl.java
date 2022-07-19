package com.bootcamp.productservice.service.impl;

import com.bootcamp.productservice.Util.GeneralException;
import com.bootcamp.productservice.Util.Util;
import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.dto.client.PersonnelDto;
import com.bootcamp.productservice.model.*;
import com.bootcamp.productservice.repository.IBankAccountRepository;
import com.bootcamp.productservice.repository.IBusinessAccountRepository;
import com.bootcamp.productservice.repository.IProduct_TypeRepository;
import com.bootcamp.productservice.service.interfaces.IBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountServiceImpl implements IBankAccountService {

    @Autowired
    private IBankAccountRepository bankAccountRepository;

    @Autowired
    private IProduct_TypeRepository product_typeRepository;

    @Autowired
    private IBusinessAccountRepository businessAccountRepository;



    private boolean isVIP= false;

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


        Mono<Product_Type> productTypeMono = product_typeRepository.findById(bankAccountDto.getProductTypeId());


        Mono<Bank_Account> bankAccountMono = productTypeMono.map(product_type -> {

            Bank_Account bank_account = new Bank_Account();
            bank_account.setAvailableBalance(bankAccountDto.getAvailableBalance());
            bank_account.setNumberAccount(bankAccountDto.getNumberAccount());
            bank_account.setComission(bankAccountDto.getComission());
            bank_account.setProduct_type(product_type);

            return bank_account;
        });


        return null; // bankAccountMono.flatMap(bank_account -> saveClientAndBankAccount(bank_account, bankAccountDto));
    }

//        private Mono<Bank_Account> saveClientAndBankAccount(Bank_Account bank_account, BankAccountDto bankAccountDto) {
//        if (bankAccountDto.getPersonnelId() != null && !bankAccountDto.getPersonnelId().equals("")) {
//
//
//        } else if (bankAccountDto.getBusinessId() != null && !bankAccountDto.getBusinessId().equals("")) {
//
//        } else {
//            return Mono.error(new GeneralException(Util.EMPTY_ID));
//        }
//
//    }


    @Override
    public Mono<Bank_Account> update(BankAccountDto bankAccountDto) {

        //get the mono of bank account by Id
        Mono<Bank_Account> bankAcountMono = bankAccountRepository.findById(bankAccountDto.getBankAccountId());
        Mono<Product_Type> productTypeMono = product_typeRepository.findById(bankAccountDto.getProductTypeId());


        bankAcountMono = Mono.zip(bankAcountMono, productTypeMono).map(/*get the bank_Account Object from mono*/data -> {
            //Set the bank account and assign the mono of bank_Account (bankAcountMono)
            Bank_Account bank_account = data.getT1();

            bank_account.setProduct_type(data.getT2());
            bank_account.setNumberAccount(bankAccountDto.getNumberAccount());
            bank_account.setAvailableBalance(bankAccountDto.getAvailableBalance());
            bank_account.setComission(bankAccountDto.getComission());
            return bank_account;
        }).flatMap(
                //save the new bank_account
                bankAccountRepository::save);

        return bankAcountMono;
    }


    @Override
    public Mono<Void> delete(String id) {

        return bankAccountRepository.deleteById(id);
    }
}
