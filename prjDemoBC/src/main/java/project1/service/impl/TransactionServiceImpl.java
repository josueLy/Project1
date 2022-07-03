package project1.service.impl;

import org.springframework.stereotype.Service;
import project1.dto.client.TransactionDto;
import project1.model.Transaction;
import project1.service.interfaces.ITransactionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements ITransactionService {
    @Override
    public Flux<Transaction> findAll() {
        return null;
    }

    @Override
    public Mono<Transaction> save(TransactionDto transactionDto) {
        return null;
    }

    @Override
    public Mono<Transaction> update(Transaction transaction) {
        return null;
    }
}
