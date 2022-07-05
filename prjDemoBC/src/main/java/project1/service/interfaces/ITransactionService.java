package project1.service.interfaces;

import project1.dto.transaction.TransactionDto;
import project1.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionService {
    Flux<Transaction> findAll();

    Mono<Transaction> show(String id);

    Mono<Transaction> save(TransactionDto transaction);

    Mono<Transaction> update(TransactionDto transaction);

    Mono<Void> delete(String id);
}
