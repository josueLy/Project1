package project1.service.interfaces;

import project1.dto.client.BusinessDto;
import project1.model.Business;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBussinesService {


    Flux<Business> findAll();

    Mono<Business> show(String id);

    Mono<Business> save(BusinessDto bussinessDto);

    Mono<Business> update(BusinessDto business);


    Mono<Void> delete(String id);
}