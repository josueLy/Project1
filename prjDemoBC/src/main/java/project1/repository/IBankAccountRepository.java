package project1.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import project1.model.Bank_Account;

@Repository
public interface IBankAccountRepository  extends ReactiveCrudRepository<Bank_Account,String> {


}
