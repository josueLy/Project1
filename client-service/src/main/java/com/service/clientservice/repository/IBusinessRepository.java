package com.service.clientservice.repository;

import com.service.clientservice.model.Business;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository

public interface IBusinessRepository extends ReactiveCrudRepository<Business, String> {

    Mono<Business> findByDni(String dni);
}
