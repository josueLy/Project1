package com.bootcamp.productservice.controller;

import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.service.interfaces.IBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/BankAccount")
public class BankAccountController {

    @Autowired
    private IBankAccountService bankAccountService;

    //list all Bank_Account
    @GetMapping("/list")
    public Flux<Bank_Account> list(){
        return bankAccountService.findAll();
    }

    //show the bank account by Id
    @GetMapping("/show/{id}")
    public  Mono<Bank_Account> show(@PathVariable("id") String bank_account_id)
    {
        return  bankAccountService.show(bank_account_id);
    }

    // create new Bank Account
    @PostMapping("/create")
    public Mono<Bank_Account> create(@RequestBody BankAccountDto bankAccountDto){
        return  bankAccountService.save(bankAccountDto);
    }

    //update bank account
    @PutMapping("/update")
    public Mono<Bank_Account> update(@RequestBody BankAccountDto bankAccountDto){
        return bankAccountService.update(bankAccountDto);
    }

    //delete bank account
    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable("id") String id)
    {
       return bankAccountService.delete(id);
    }

}
