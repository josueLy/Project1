package project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project1.dto.transaction.TransactionDto;
import project1.model.Transaction;
import project1.service.interfaces.ITransactionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    //list all Transactions
    @GetMapping("/list")
    public Flux<Transaction> list(){
        return transactionService.findAll();
    }

    //show the Transaction by Id
    @GetMapping("/show/{id}")
    public  Mono<Transaction> show(@PathVariable("id") String transactionId)
    {
        return  transactionService.show(transactionId);
    }

    // create new Transaction
    @PostMapping("/create")
    public Mono<Transaction> create (@RequestBody TransactionDto transactionDto){
        return transactionService.save(transactionDto);
    }

    //modify a Transaction
    @PutMapping("/update")
    public Mono<Transaction> update(@RequestBody TransactionDto transaction){
        return transactionService.update(transaction);
    }

    //delete a Transaction
    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable("id") String id)
    {
        return transactionService.delete(id);
    }
}
