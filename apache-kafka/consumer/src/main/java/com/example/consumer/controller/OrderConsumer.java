package com.example.consumer.controller;

import com.example.consumer.model.OrderPlacedEvent;
import com.example.consumer.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.net.SocketException;

@Component
@Slf4j
public class OrderConsumer {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment environment;

    @KafkaListener(topics = "orders-placed", groupId = "order-consumer-group")
    @RetryableTopic(
            backoff = @Backoff(delay = 600000L, multiplier = 3.0, maxDelay = 5400000L),
            attempts = "4",
            autoCreateTopics = "false",
            include = {SocketException.class, RuntimeException.class}
    )
    public void consumeOrder(OrderPlacedEvent orderPlacedEvent) {
        log.info("order-event " + environment.getProperty("server.port"));
        log.info(orderPlacedEvent.toString());

//        int eventId = orderService.saveEvent(orderPlacedEvent);
//        log.info("Event id is {}", eventId);

        try{
            int eventId = orderService.saveEvent(orderPlacedEvent);
            log.info("Event id is {}", eventId);
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }

    @DltHandler
    public void processMessage(OrderPlacedEvent orderPlacedEvent, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.error("Event from topic "+topic+" is dead lettered - event:" );
        log.info(orderPlacedEvent.toString());
    }
}
