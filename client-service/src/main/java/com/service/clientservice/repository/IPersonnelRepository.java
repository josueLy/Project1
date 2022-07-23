package com.service.clientservice.repository;

import com.service.clientservice.model.Personnel;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IPersonnelRepository extends ReactiveCrudRepository<Personnel,String> {

    @Query("{'dni': ?0}")
    Mono<Personnel> ShowByDni(String dni);
}
