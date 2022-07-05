package project1.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import project1.model.Credit;

@Repository
public interface ICreditRepository extends ReactiveCrudRepository<Credit,String> {
}
