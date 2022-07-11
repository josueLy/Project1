package com.bootcamp.productservice.service.interfaces;

import com.bootcamp.productservice.dto.credit.CreditDto;
import com.bootcamp.productservice.model.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditService {

    Flux<Credit> list();
//
    Mono<Credit> show(String creditId);
//
    Mono<Credit> create(CreditDto creditDto);

    Mono<Credit> update(CreditDto bankAccountDto);

    Mono<Void> delete(String id);

}
