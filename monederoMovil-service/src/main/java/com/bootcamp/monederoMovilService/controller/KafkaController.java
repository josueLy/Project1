package com.bootcamp.monederoMovilService.controller;


import com.bootcamp.monederoMovilService.dto.client.PersonnelDto;
import com.bootcamp.monederoMovilService.dto.monedero.MonederoDto;
import com.bootcamp.monederoMovilService.producer.KafkaStringProducer;
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
    public void sendMessageToKafkaTopic (@RequestBody PersonnelDto personnelDto){
        String message = new Gson().toJson(personnelDto);
        this.kafkaStringProducer.sendMessageC(message);
    }

    @PostMapping(value = "/send-pay")
    public void sendPayment (@RequestBody MonederoDto monederoDto){
        String message = new Gson().toJson(monederoDto);
        this.kafkaStringProducer.sendMenssageToUpdateAccount(message);
    }


    @PostMapping(value = "/receive-pay")
    public void receivePayment (@RequestBody MonederoDto monederoDto){
        String message = new Gson().toJson(monederoDto);
        this.kafkaStringProducer.sendMenssageToUpdateAccount(message);
    }
}
