package com.bootcamp.monederoMovilService.controller;


import com.bootcamp.monederoMovilService.dto.client.PersonnelDto;
import com.bootcamp.monederoMovilService.model.Personnel;
import com.bootcamp.monederoMovilService.producer.KafkaStringProducer;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final KafkaStringProducer kafkaStringProducer;

    @Autowired
    KafkaController(KafkaStringProducer kafkaStringProducer) {
        this.kafkaStringProducer = kafkaStringProducer;
    }
   /* @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message){

        this.kafkaStringProducer.sendMessage(message);
    }
    */
    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic (@RequestBody PersonnelDto personnelDto){
        String message = new Gson().toJson(personnelDto);
        this.kafkaStringProducer.sendMessageC(message);
    }
}
