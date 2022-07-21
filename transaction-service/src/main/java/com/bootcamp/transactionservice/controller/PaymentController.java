package com.bootcamp.transactionservice.controller;


import com.bootcamp.transactionservice.dto.payment.PaymentDto;
import com.bootcamp.transactionservice.model.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/Payment")
public class PaymentController {

//    @PostMapping("/listByClient")
//    public Flux<Transaction> list(PaymentDto paymentDto){
//        return transactionService.findAll();
//    }
}
