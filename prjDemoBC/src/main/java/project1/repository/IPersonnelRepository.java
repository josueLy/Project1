package project1.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import project1.model.Personnel;
import reactor.core.publisher.Flux;

@Repository
public interface IPersonnelRepository extends ReactiveCrudRepository<Personnel,String> {
    @Query("{'dni': ?0}")
    Flux<Personnel> ShowByDni(String dni);
}
