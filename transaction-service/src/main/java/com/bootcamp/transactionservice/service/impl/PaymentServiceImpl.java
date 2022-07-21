package com.bootcamp.transactionservice.service.impl;

import com.bootcamp.transactionservice.dto.payment.PaymentDto;
import com.bootcamp.transactionservice.model.Payment;
import com.bootcamp.transactionservice.model.Personnel;
import com.bootcamp.transactionservice.model.Quota;
import com.bootcamp.transactionservice.repository.IPaymentRepository;
import com.bootcamp.transactionservice.service.interfaces.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Flux<Quota> listQuotas(PaymentDto paymentDto) {

        return  null;
    }

    private Flux<Quota> getQuotas(PaymentDto paymentDto)
    {
        if(paymentDto.getPersonnelId()!=null && !paymentDto.getPersonnelId().equals(""))
        {
            Flux<Payment> paymentFlux= getPersonnelPayments(paymentDto);


//            return  paymentFlux.collectList().flatMap(payments -> {
//
//            });
        }

        if(paymentDto.getBusinessId()!=null && !paymentDto.getBusinessId().equals(""))
        {

        }

        return  null;

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
           return  paymentRepository.findByPersonnelPayment_DateBetween(personnel,paymentDto.getStartDate(),paymentDto.getEndDate());
        });

    }

    //private Flux<Quota> g

}
