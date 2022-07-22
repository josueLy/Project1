package com.service.clientservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class KafkaStringConsumer {

    Logger logger = LoggerFactory.getLogger(KafkaStringConsumer.class);

    @KafkaListener(topics = "bootcamp-proyecto4", groupId = "group_id")
    public void consume(String message){
        logger.info("Consuming message {}",message);
    }

}
