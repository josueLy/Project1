package project1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project1.dto.client.TransactionDto;
import project1.model.Personnel;
import project1.model.Transaction;
import project1.repository.ITransactionRepository;
import project1.service.interfaces.ITransactionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private ITransactionRepository transactionRepository;
    @Override
    public Flux<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Mono<Transaction> save(TransactionDto transaction) {
        Transaction transactionObj = new Transaction();

        return transactionRepository.save(transactionObj);

    }

    @Override
    public Mono<Transaction> update(Transaction transaction) {
        return null;
    }
}
