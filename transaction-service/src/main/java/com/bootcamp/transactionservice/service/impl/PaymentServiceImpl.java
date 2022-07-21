package com.bootcamp.transactionservice.service.impl;

import com.bootcamp.transactionservice.service.interfaces.Util.GeneralException;
import com.bootcamp.transactionservice.service.interfaces.Util.Util;
import com.bootcamp.transactionservice.dto.payment.PaymentDto;
import com.bootcamp.transactionservice.model.*;
import com.bootcamp.transactionservice.repository.IPaymentRepository;
import com.bootcamp.transactionservice.service.interfaces.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Flux<Quota> listQuotas(PaymentDto paymentDto) {

        return  getQuotas(paymentDto);
    }

    private Flux<Quota> getQuotas(PaymentDto paymentDto)
    {
        if(paymentDto.getPersonnelId()!=null && !paymentDto.getPersonnelId().equals(""))
        {
            Flux<Payment> paymentFlux= getPersonnelPayments(paymentDto);

            return  paymentFlux
                    .collectList()
                    .flatMapMany(payments -> {
                    return getQuotas(payments);
            });
        }
        else if(paymentDto.getBusinessId()!=null && !paymentDto.getBusinessId().equals(""))
        {
            Flux<Payment> paymentFlux= getBusinessPayments(paymentDto);

            return  paymentFlux
                    .collectList()
                    .flatMapMany(payments -> {
                        return getQuotas(payments);
                    });
        }else
        {
            return Flux.error(new GeneralException(Util.EMPTY_ID));
        }

    }

    private  Flux<Payment> getPersonnelPayments(PaymentDto paymentDto)
    {
        Mono<Personnel> personnelMono =
                webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8085/personnel/showById/" + paymentDto.getPersonnelId()
                        )
                        .retrieve()
                        .bodyToMono(Personnel.class);


        return personnelMono.flatMapMany(personnel -> {
           return  paymentRepository.findAllByPersonnelAndPaymentDateBetween(personnel,paymentDto.getStartDate(),paymentDto.getEndDate());
        });

    }

    private  Flux<Payment> getBusinessPayments(PaymentDto paymentDto)
    {
        Mono<Business> businessMono =
                webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8085/business/show/" + paymentDto.getBusinessId()
                        ).retrieve()
                        .bodyToMono(Business.class);

        return businessMono.flatMapMany(business -> {
            return  paymentRepository.findAllByBusinessAndPaymentDateBetween(business,paymentDto.getStartDate(),paymentDto.getEndDate());
        });
    }

    private Flux<Quota> getQuotas(List<Payment> payments) {
        List<Quota> quotas = new ArrayList<>();
        for (Payment payment : payments) {
            quotas.addAll(payment.getQuotas());
        }

        return Mono.just(quotas)
                .flatMapMany(Flux::fromIterable)
                .log();
    }
    //4.
    private Flux<Payment> getBussinesP (PaymentDto paymentDto){
        Mono<Business> businessMono =
                webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8085/business/show/"+ paymentDto.getBusinessId())
                        .retrieve()
                        .bodyToMono(Business.class);
        return businessMono.flatMapMany(business -> {
            return paymentRepository.findAllByBusinessAndBankAccount(business,new Bank_Account());
        });
    }
    private Flux<Payment> getPersonelP (PaymentDto paymentDto){
        Mono<Business> businessMono =
                webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8085/business/show/"+ paymentDto.getPersonnelId())
                        .retrieve()
                        .bodyToMono(Business.class);
        return businessMono.flatMapMany(business -> {
            return paymentRepository.findAllByPersonnelAndBankAccount(new Personnel(),new Bank_Account());
        });
    }



    //update's method

    //get  all Payments with query if businessId is not null execute business Query
    // fill all quotas of obtained payments
    // find the first expirationDate of  quota that status is false(still not payed) and change by true(payed)
    // Mono<Payment>  where get the payment by Quota and the client is different update the payment else nothing to do


    //get  all Payments with query  if Personnel id is not null execute personnel query
    // fill all quotas of obtained payments
    // find the first expirationDate of  quota that status is false(still not payed) and change by true(payed)
    // Mono<Payment>  where get the payment by Quota and the client is different update the payment else nothing to do


}
