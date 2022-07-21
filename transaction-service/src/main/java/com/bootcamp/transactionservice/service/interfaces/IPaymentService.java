package com.bootcamp.transactionservice.service.interfaces;

import com.bootcamp.transactionservice.dto.payment.PaymentDto;
import com.bootcamp.transactionservice.model.Payment;
import com.bootcamp.transactionservice.model.Quota;
import reactor.core.publisher.Flux;

public interface IPaymentService {

    Flux<Quota> listQuotas(PaymentDto paymentDto);
}
