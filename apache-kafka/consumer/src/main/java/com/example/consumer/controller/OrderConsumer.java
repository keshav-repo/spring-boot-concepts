package com.example.consumer.controller;

import com.example.consumer.model.OrderPlacedEvent;
import com.example.consumer.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderConsumer {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;
    @KafkaListener(topics = "orders-placed", groupId = "order-consumer-group")
    public void consumeOrder(OrderPlacedEvent orderPlacedEvent) {
        log.info("order-event");
        log.info(orderPlacedEvent.toString());
        int eventId = orderService.saveEvent(orderPlacedEvent);
        log.info("Event id is {}", eventId);
    }
}
