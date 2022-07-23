package com.service.clientservice.service.interfaces;

import com.service.clientservice.dto.client.BusinessDto;
import com.service.clientservice.model.Business;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBussinesService {


    Flux<Business> findAll();

    Mono<Business> show(String id);

    Mono<Business> showDni(String id);


    Mono<Business> save(BusinessDto bussinessDto);

    Mono<Business> update(BusinessDto business);


    Mono<Void> delete(String id);
}