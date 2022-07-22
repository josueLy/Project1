package com.bootcamp.productservice.repository;

import com.bootcamp.productservice.model.Bank_Account;
import com.bootcamp.productservice.model.Business;
import com.bootcamp.productservice.model.Business_Account;
import com.bootcamp.productservice.model.Personnel;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Date;


@Repository
public interface IBankAccountRepository  extends ReactiveCrudRepository<Bank_Account,String> {

    Flux<Bank_Account> findAllPersonnelByCreationDateBetween(Personnel personnel, Date startDate, Date endDate);
    Flux<Bank_Account> findAllBusinessByCreationDateBetween(Business business, Date startDate, Date endDate);

}
