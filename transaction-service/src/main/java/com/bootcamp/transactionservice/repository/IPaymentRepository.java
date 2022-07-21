package com.bootcamp.transactionservice.repository;

import com.bootcamp.transactionservice.model.Business;
import com.bootcamp.transactionservice.model.Payment;
import com.bootcamp.transactionservice.model.Personnel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface IPaymentRepository extends ReactiveCrudRepository<Payment,String>
{
    Flux<Payment> findAllByPersonnelAndPaymentDateBetween(Personnel personnel, Date startDate, Date endDate);

    Flux<Payment> findAllByBusinessAndPaymentDateBetween(Business business, Date startDate, Date endDate);
}
