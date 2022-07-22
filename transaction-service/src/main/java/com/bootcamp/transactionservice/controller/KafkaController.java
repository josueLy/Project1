package com.bootcamp.transactionservice.controller;


import com.bootcamp.transactionservice.dto.transaction.TransactionDto;
import com.bootcamp.transactionservice.producer.KafkaStringProducer;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final KafkaStringProducer kafkaStringProducer;

    @Autowired
    KafkaController(KafkaStringProducer kafkaStringProducer) {
        this.kafkaStringProducer = kafkaStringProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestBody TransactionDto transactionDto){
        String message = new Gson().toJson(transactionDto);
        this.kafkaStringProducer.sendMessage(message);
    }
}
