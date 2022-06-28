package com.nttdata.events;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {


    public EventHandler(){

    }
    private final Logger LOGGER= LoggerFactory.getLogger("EventHandler");
    @KafkaListener(
            topics = "bootcoins",
            containerFactory = "kafkaListenerContainerFactory",
            groupId = "grupo3")
    public void consumer(Event<?> event){
        if(event.getClass().isAssignableFrom(BuyBootCoinEvent.class)){
            BuyBootCoinEvent bootCoinEvent=(BuyBootCoinEvent)event;
            LOGGER.info("# numero id coins {}",bootCoinEvent.getData().getCoinsId());
            throw new RuntimeException("dasdasdas");

        }

    }



}
