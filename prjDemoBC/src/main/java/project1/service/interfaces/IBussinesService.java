package project1.service.interfaces;

import project1.model.Business;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBussinesService {

    Flux<Business> findAll();

    Mono<Business> save(Business business);

    Mono<Business> update(Business business);


    public Mono<Void> delete(Business business);
}
