package com.nttdata.events;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EventDispatcher {
    private String topicDeposit="deposit";
    private final KafkaTemplate<String,Event<?>> kafkaTemplate;
    public EventDispatcher(KafkaTemplate<String,Event<?>> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void publish(Deposit deposit){
        ProductDepositEvent event=new ProductDepositEvent();
        event.setId(UUID.randomUUID().toString());
        event.setCreatedAt(LocalDateTime.now());
        event.setData(deposit);
        kafkaTemplate.send(topicDeposit,event);
    }
}
