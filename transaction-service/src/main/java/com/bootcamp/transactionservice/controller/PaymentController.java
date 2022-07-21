package com.bootcamp.transactionservice.controller;


import com.bootcamp.transactionservice.dto.payment.PaymentDto;
import com.bootcamp.transactionservice.model.Quota;
import com.bootcamp.transactionservice.model.Transaction;
import com.bootcamp.transactionservice.service.interfaces.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping("/listQuotas")
    public Flux<Quota> listQuotas(@RequestBody PaymentDto paymentDto){
        return paymentService.listQuotas(paymentDto);
    }




}
