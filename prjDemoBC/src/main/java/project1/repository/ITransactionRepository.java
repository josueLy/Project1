package project1.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import project1.model.Transaction;

@Repository
public interface ITransactionRepository extends ReactiveCrudRepository<Transaction,String> {

}
