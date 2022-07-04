package project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project1.dto.client.Product_TypeDto;
import project1.dto.client.TransactionDto;
import project1.model.Product_Type;
import project1.model.Transaction;
import project1.service.interfaces.ITransactionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @GetMapping("/list")
    public Flux<Transaction> list(){
        return transactionService.findAll();
    }
    @PostMapping("/create")
    public Mono<Transaction> create (@RequestBody TransactionDto transactionDto){
        return transactionService.save(transactionDto);
    }
    @PutMapping("/update")
    public Mono<Transaction> update(@RequestBody Transaction transaction){
        return transactionService.update(transaction);
    }
}
