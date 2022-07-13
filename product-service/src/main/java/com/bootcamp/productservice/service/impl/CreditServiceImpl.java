package com.bootcamp.productservice.service.impl;

import com.bootcamp.productservice.dto.credit.CreditDto;
import com.bootcamp.productservice.model.Business;
import com.bootcamp.productservice.model.Credit;
import com.bootcamp.productservice.model.Product_Type;
import com.bootcamp.productservice.repository.ICreditRepository;
import com.bootcamp.productservice.repository.IProduct_TypeRepository;
import com.bootcamp.productservice.service.interfaces.ICreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class CreditServiceImpl implements ICreditService {

    @Autowired
    private ICreditRepository creditRepository;

    @Autowired
    private IProduct_TypeRepository productTypeRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Flux<Credit> list() {
        return creditRepository.findAll();
    }

    @Override
    public Mono<Credit> show(String creditId) {
        return creditRepository.findById(creditId);
    }

    @Override
    public Mono<Credit> create(CreditDto creditDto) {

        Mono<Business> businessMono = webClientBuilder.build()
                .get()
                .uri("http://localhost:8085/business/show/" + creditDto.getBusinessId())
                .retrieve()
                .bodyToMono(Business.class);

        Mono<Product_Type> productTypeMono= productTypeRepository.findById(creditDto.getProductTypeId());

        Mono<Credit> creditMono = Mono.zip(businessMono,productTypeMono)
                .map(data->{
                    Credit credit = new Credit();
                    credit.setBusiness(data.getT1());
                    credit.setProduct_type(data.getT2());
                    credit.setAvailableBalance(creditDto.getAvailableBalance());
                    credit.setNumberAccount(creditDto.getNumberAccount());
                    credit.setInterestRate(creditDto.getInterestRate());

                    return credit;
                }).flatMap(creditRepository::save);

        return creditMono;
    }

    @Override
    public Mono<Credit> update(CreditDto creditDto) {

        Mono<Credit> creditMono= creditRepository.findById(creditDto.getCreditId());
        Mono<Business> businessMono = webClientBuilder.build()
                .get()
                .uri("http://localhost:8085/business/show/" + creditDto.getBusinessId())
                .retrieve()
                .bodyToMono(Business.class);
        Mono<Product_Type> productTypeMono =productTypeRepository.findById(creditDto.getProductTypeId());

        creditMono= Mono.zip(creditMono,businessMono,productTypeMono).map(data->{
           Credit credit = data.getT1();

           credit.setBusiness(data.getT2());
           credit.setProduct_type(data.getT3());
           credit.setAvailableBalance(creditDto.getAvailableBalance());
           credit.setNumberAccount(creditDto.getNumberAccount());
           credit.setInterestRate(creditDto.getInterestRate());

           return  credit;
        }).flatMap(creditRepository::save);


        return  creditMono;
    }

    @Override
    public Mono<Void> delete(String id) {
       return  creditRepository.deleteById(id);
    }
}
