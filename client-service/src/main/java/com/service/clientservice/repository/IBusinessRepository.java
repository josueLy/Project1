package com.service.clientservice.repository;

import com.service.clientservice.model.Business;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface IBusinessRepository extends ReactiveCrudRepository<Business, String> {

}
