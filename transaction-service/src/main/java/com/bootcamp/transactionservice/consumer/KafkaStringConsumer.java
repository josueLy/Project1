package com.bootcamp.transactionservice.consumer;

import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;


@Component
public class KafkaStringConsumer {

    Logger logger = LoggerFactory.getLogger(KafkaStringConsumer.class);

    @KafkaListener(topics = "bootcamp-topic", groupId = "group_id")
    public void consume(String message){
        logger.info("Consuming message {}",message);
    }

}
