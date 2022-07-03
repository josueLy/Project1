package project1.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import project1.model.Personnel;

@Repository
public interface IPersonnelRepository extends ReactiveCrudRepository<Personnel,String> {
}
