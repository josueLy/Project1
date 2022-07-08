package com.bootcamp.transactionservice.repository;

import com.bootcamp.transactionservice.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends ReactiveCrudRepository<Transaction,String> {

}
