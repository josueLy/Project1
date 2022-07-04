package project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project1.dto.bankAccount.BankAccountDto;
import project1.dto.businessAccount.BusinessAccountDto;
import project1.model.Bank_Account;
import project1.model.Business_Account;
import project1.service.interfaces.IBankAccountService;
import project1.service.interfaces.IBusinessAccountService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/BusinessAccount")
public class BusinessAccountController {

    @Autowired
    private IBusinessAccountService businessAccountService;

    @GetMapping("/list")
    public Flux<Business_Account> list(){
        return businessAccountService.findAll();
    }

    @PostMapping("/create")
    public Mono<Business_Account> create(@RequestBody BusinessAccountDto businessAccountDto){
        return  businessAccountService.save(businessAccountDto);
    }
    @PutMapping("/update")
    public Mono<Business_Account> update(@RequestBody BusinessAccountDto businessAccountDto){
        return businessAccountService.update(businessAccountDto);
    }
}
