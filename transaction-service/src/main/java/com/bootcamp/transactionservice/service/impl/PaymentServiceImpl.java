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

    private Flux<Quota> getQuotas(List<Payment> payments)
    {
        List<Quota> quotas=  new ArrayList<>();
      for(Payment payment: payments)
      {
        quotas.addAll(payment.getQuotas());
      }

      return  Mono.just(quotas)
              .flatMapMany(Flux::fromIterable)
              .log();
    }

}
