package com.bootcamp.productservice.service.impl;

import com.bootcamp.productservice.dto.credit.CreditDto;
import com.bootcamp.productservice.model.Business;
import com.bootcamp.productservice.model.Credit;
import com.bootcamp.productservice.repository.ICreditRepository;
import com.bootcamp.productservice.service.interfaces.ICreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class CreditServiceImpl implements ICreditService {

    @Autowired
    private ICreditRepository creditRepository;

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
//
        Mono<Credit> creditMono = webClientBuilder.build()
                .get()
                .uri("http://localhost:8085/business/show/" + creditDto.getBusinessId())
                .retrieve()
                .bodyToMono(Business.class)
                .map(business -> {
//
                    Credit credit = new Credit();
//
                    credit.setBusiness(business);
                    credit.setInterestRate(creditDto.getInterestRate());

                    return credit;

                });

        creditMono= creditMono.flatMap(entity -> {
            return creditRepository.save(entity);
        });


        return creditMono;
    }

//    @Override
//    public Mono<Credit> update(CreditDto creditDto) {
//
//        Mono<Credit> creditMono= creditRepository.findById(creditDto.getCreditId());
//        Mono<Business> businessMono = businessRepository.findById(creditDto.getBusinessId());
//
//
//        creditMono= Mono.zip(creditMono,businessMono).map(data->{
//           Credit credit = data.getT1();
//
//           credit.setBusiness(data.getT2());
//           credit.setInterestRate(creditDto.getInterestRate());
//
//           return  credit;
//        });
//
//        creditMono= creditMono.flatMap(result->{
//           return  creditRepository.save(result);
//        });
//
//        return  creditMono;
//    }

    @Override
    public Mono<Void> delete(String id) {
       return  creditRepository.deleteById(id);
    }
}
