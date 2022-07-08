package com.bootcamp.productservice.service.interfaces;

import com.bootcamp.productservice.dto.product_type.Product_TypeDto;
import com.bootcamp.productservice.model.Product_Type;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProduct_TypeService {

    Flux<Product_Type> findAll();

    Mono<Product_Type> save(Product_TypeDto product_type);

    Mono<Product_Type> update(Product_Type product_type);

    Mono<Product_Type> ShowById(String typeId);

    Mono<Void> delete (String id);

}
