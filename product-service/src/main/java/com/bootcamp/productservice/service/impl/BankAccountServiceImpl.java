package com.bootcamp.productservice.service.impl;

import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.model.Product_Type;
import com.bootcamp.productservice.repository.IBankAccountRepository;
import com.bootcamp.productservice.repository.IProduct_TypeRepository;
import com.bootcamp.productservice.service.interfaces.IBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Service
public class BankAccountServiceImpl implements IBankAccountService {

    @Autowired
    private IBankAccountRepository bankAccountRepository;

    @Autowired
    private IProduct_TypeRepository product_typeRepository;

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


/*
        Bank_Account bank_acount = new Bank_Account();

        Predicate<Bank_Account> bank_accountPredicate = (s) -> s.getMoountMinA() >= 0 ;
        bank_acount.setAvailableBalance(bankAccountDto.getAvailableBalance());
        bank_acount.setNumberAccount(bankAccountDto.getNumberAccount());
        bank_acount.setComission(bankAccountDto.getComission());



        //bank_acount.setAvailableBalance(bankAccountDto.getAvailableBalance());
        //bank_acount.setNumberAccount(bankAccountDto.getNumberAccount());
        //bank_acount.setComission(bankAccountDto.getComission());

        return bankAccountRepository.save(bank_acount);
 */
         Mono<Product_Type> productTypeMono = product_typeRepository.findById(bankAccountDto.getProductTypeId());

         Mono<Bank_Account> bankAccountMono = productTypeMono.map(product_type->{


             Bank_Account bank_account = new Bank_Account();
             bank_account.setAvailableBalance(bankAccountDto.getAvailableBalance());
             bank_account.setNumberAccount(bankAccountDto.getNumberAccount());
             bank_account.setComission(bankAccountDto.getComission());
             bank_account.setProduct_type(product_type);

             return  bank_account;
         });

        return  bankAccountMono.flatMap(bankAccountRepository::save);
    }

    @Override
    public Mono<Bank_Account> update(BankAccountDto bankAccountDto) {
        //get the mono of bank account by Id
        Mono<Bank_Account> bankAcountMono = bankAccountRepository.findById(bankAccountDto.getBankAccountId());
        Mono<Product_Type> productTypeMono = product_typeRepository.findById(bankAccountDto.getProductTypeId());

        bankAcountMono = Mono.zip(bankAcountMono,productTypeMono).map(/*get the bank_Account Object from mono*/data -> {
            //Set the bank account and assign the mono of bank_Account (bankAcountMono)
            Bank_Account bank_account  =data.getT1();

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
