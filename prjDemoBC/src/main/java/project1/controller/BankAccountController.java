package project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project1.dto.bankAccount.BankAccountDto;
import project1.model.Bank_Account;
import project1.service.interfaces.IBankAccountService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/BankAccount")
public class BankAccountController {

    @Autowired
    private IBankAccountService bankAccountService;

    @GetMapping("/list")
    public Flux<Bank_Account> list(){
        return bankAccountService.findAll();
    }

    @PostMapping("/create")
    public Mono<Bank_Account> create(@RequestBody BankAccountDto bankAccountDto){
        return  bankAccountService.save(bankAccountDto);
    }
    @PutMapping("/update")
    public Mono<Bank_Account> update(@RequestBody BankAccountDto bankAccountDto){
        return bankAccountService.update(bankAccountDto);
    }

}
