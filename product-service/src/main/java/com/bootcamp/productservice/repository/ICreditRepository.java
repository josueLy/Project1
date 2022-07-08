package com.bootcamp.productservice.repository;

import com.bootcamp.productservice.model.Credit;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICreditRepository extends ReactiveCrudRepository<Credit,String> {
}
