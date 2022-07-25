package com.bootcamp.productservice.service.impl;

import com.bootcamp.productservice.dto.product_type.Product_TypeDto;
import com.bootcamp.productservice.model.*;
import com.bootcamp.productservice.repository.IProduct_TypeRepository;
import com.bootcamp.productservice.service.interfaces.IProduct_TypeService;
import com.bootcamp.productservice.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class Product_TypeServiceImpl implements IProduct_TypeService {

    @Autowired
    private IProduct_TypeRepository product_typeRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private RedisService redisService;

    @Override
    public Flux<Product_Type> findAll() {
        return product_typeRepository.findAll();
    }

    @Override
    public Mono<Product_Type> save(Product_TypeDto product_type) {


        Product_Type product_typeObj = new Product_Type();

        product_typeObj.setDescription(product_type.getDescription());

        redisService.saveProductType(product_type.getTypeId(),product_typeObj);

        return product_typeRepository.save(product_typeObj);

    }

    @Override
    public Mono<Product_Type> update(Product_Type product_type) {

        Mono<Product_Type> product_typeMono = product_typeRepository.findById(product_type.getTypeId());

        product_typeMono = product_typeMono.map(result ->{
            result.setDescription(product_type.getDescription());

            return result;
        }).flatMap(result -> product_typeRepository.save(result));


        return product_typeMono;
    }

    @Override
    public Mono<Product_Type> ShowById(String typeId) {
        return product_typeRepository.findById(typeId);
    }

    @Override
    public Mono<Void> delete(String id) {
        return product_typeRepository.deleteById(id);
    }
}
