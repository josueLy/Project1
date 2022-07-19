package com.bootcamp.productservice.controller;

import com.bootcamp.productservice.dto.BusinessAcount.AccountDto;
import com.bootcamp.productservice.dto.bankAccount.BankAccountDto;
import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.model.Business_Account;
import com.bootcamp.productservice.service.interfaces.IBankAccountService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    @CircuitBreaker(name = "BankAccountCB", fallbackMethod = "fallbackShowBankAccount")
    @GetMapping("/show/{id}")
    public  Mono<Bank_Account> show(@PathVariable("id") String bank_account_id)
    {
        return  bankAccountService.show(bank_account_id);
    }
    private Mono<Bank_Account>fallbackShowBankAccount(String bank_account_id,Exception ex ){
        System.out.println("inside backup "+ bankAccountService.show(bank_account_id));
        return null;
    }

    @PostMapping("/showAccountsByClient")
    public Flux<Bank_Account> getAccountsByClient(@RequestBody AccountDto accountDto)
    {
        return  bankAccountService.showAccountsByClient(accountDto);
    }

    // create new Bank Account
    // implementation of CircuitBreaker
    @CircuitBreaker(name = "BankAccountCB", fallbackMethod = "fallbackCreateBankAccount")
    @PostMapping("/create")
    public Mono<Bank_Account> create(@RequestBody BankAccountDto bankAccountDto){
        return  bankAccountService.save(bankAccountDto);
    }
    private  Mono<Bank_Account> fallbackCreateBankAccount ( BankAccountDto bankAccountDto,Throwable t){
        System.out.println("inside backup product"+ bankAccountService.save(bankAccountDto));
        return null;
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
