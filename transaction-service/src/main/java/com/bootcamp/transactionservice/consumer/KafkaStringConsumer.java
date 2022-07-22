package com.bootcamp.transactionservice.consumer;

import com.bootcamp.transactionservice.dto.transaction.TransactionDto;
import com.bootcamp.transactionservice.model.Transaction;
import com.bootcamp.transactionservice.service.interfaces.ITransactionService;
import com.google.gson.Gson;
import com.netflix.discovery.converters.Auto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;


@Component
public class KafkaStringConsumer {

    Logger logger = LoggerFactory.getLogger(KafkaStringConsumer.class);

    @Autowired
    private ITransactionService transactionService;


    @KafkaListener(topics = "bootcamp-topic", groupId = "group_id")
    public void consume(String message){

        TransactionDto transactionDto = new Gson().fromJson(message,TransactionDto.class);

        transactionService.save(transactionDto);

        logger.info("Consuming message {}",message);
    }

}
