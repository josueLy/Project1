package project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project1.dto.bankAccount.BankAccountDto;
import project1.dto.credit.CreditDto;
import project1.model.Bank_Account;
import project1.model.Credit;
import project1.service.interfaces.IBankAccountService;
import project1.service.interfaces.ICreditService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    private ICreditService creditService;

    @GetMapping("/list")
    public Flux<Credit> list(){
        return creditService.list();
    }

    @GetMapping("/show/{id}")
    public Mono<Credit> show(@PathVariable("id") String creditId)
    {
        return  creditService.show(creditId);
    }

    @PostMapping("/create")
    public Mono<Credit> create(@RequestBody CreditDto creditDto){
        return  creditService.create(creditDto);
    }
    @PutMapping("/update")
    public Mono<Credit> update(@RequestBody CreditDto creditDto){
        return creditService.update(creditDto);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable("id") String id)
    {
        return creditService.delete(id);
    }
}
