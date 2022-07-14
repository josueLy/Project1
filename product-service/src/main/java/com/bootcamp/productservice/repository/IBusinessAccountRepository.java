package com.bootcamp.productservice.repository;

import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.model.Business;
import com.bootcamp.productservice.model.Business_Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IBusinessAccountRepository extends ReactiveCrudRepository<Business_Account,String> {

    Flux<Business_Account> findAll(Business business);
}
