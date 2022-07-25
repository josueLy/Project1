package com.bootcamp.productservice.repository;

import com.bootcamp.productservice.model.Business;
import com.bootcamp.productservice.model.Business_Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface IBusinessAccountRepository extends ReactiveCrudRepository<Business_Account,String> {

    Flux<Business_Account> findAllByBusiness(Business business);
}
