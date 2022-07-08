package com.bootcamp.productservice.repository;

import com.bootcamp.productservice.model.Bank_Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IBankAccountRepository  extends ReactiveCrudRepository<Bank_Account,String> {


}
