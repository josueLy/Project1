package project1.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import project1.model.Business_Account;

@Repository
public interface IBusinessAccountRepository extends ReactiveCrudRepository<Business_Account,String> {
}
