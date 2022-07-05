package project1.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import project1.model.Personnel;
import project1.model.Product_Type;
import reactor.core.publisher.Flux;

@Repository
public interface IProduct_TypeRepository extends ReactiveCrudRepository<Product_Type, String> {



}
