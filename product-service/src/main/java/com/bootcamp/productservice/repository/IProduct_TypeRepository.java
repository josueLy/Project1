package com.bootcamp.productservice.repository;

import com.bootcamp.productservice.model.Product_Type;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IProduct_TypeRepository extends ReactiveCrudRepository<Product_Type, String> {



}
