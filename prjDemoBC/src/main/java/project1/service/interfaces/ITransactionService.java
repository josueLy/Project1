package project1.service.interfaces;

import project1.dto.client.Product_TypeDto;
import project1.dto.client.TransactionDto;
import project1.model.Product_Type;
import project1.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionService {
    Flux<Transaction> findAll();

    Mono<Transaction> save(TransactionDto transactionDto);

    Mono<Transaction> update(Transaction transaction);

}
