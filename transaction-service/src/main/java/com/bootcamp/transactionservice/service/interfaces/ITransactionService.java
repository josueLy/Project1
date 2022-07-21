package com.bootcamp.transactionservice.service.interfaces;

import com.bootcamp.transactionservice.dto.transaction.TransactionDto;
import com.bootcamp.transactionservice.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionService {
    Flux<Transaction> findAll();

    Mono<Transaction> show(String id);


//   Mono<Transaction> save(TransactionDto transaction);
//
//    Mono<Transaction> update(TransactionDto transaction);

    Mono<Transaction> save(TransactionDto transaction);

    Mono<Transaction> update(TransactionDto transaction);

    Mono<Void> delete(String id);
}
