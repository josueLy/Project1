package com.bootcamp.monederoMovilService.producer;

import com.bootcamp.monederoMovilService.dto.client.PersonnelDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;



@Component
public class KafkaStringProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaStringProducer.class);

    private final KafkaTemplate<String,String> kafkaTemplate;


    public KafkaStringProducer(@Qualifier("kafkaStringTemplate") KafkaTemplate<String, String> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessageC(String message){
        LOGGER.info("Producing message {}",message);
        this.kafkaTemplate.send("bootcamp-proyecto4",message);

    }

    public void sendMenssageToUpdateAccount(String message)
    {
        LOGGER.info("Producing message {}",message);
        this.kafkaTemplate.send("recieve-send-topic",message);
    }

}
