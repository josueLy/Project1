package project1.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import project1.model.Business;
import reactor.core.publisher.Mono;

@Repository

public interface IBusinessRepository extends ReactiveCrudRepository<Business, String> {

}
