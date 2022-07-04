package project1.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import project1.model.Product_Type;

@Repository
public interface IProduct_TypeRepository extends ReactiveCrudRepository<Product_Type, String> {
}
