package com.example.service;

import com.example.model.OrderPlacedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    public void sendOrder(OrderPlacedEvent orderPlacedEvent){
        String orderPlacedEventString = null;
        try {
            orderPlacedEventString = objectMapper.writeValueAsString(orderPlacedEvent);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
        CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.sendDefault(String.valueOf(orderPlacedEvent.getOrderId()), orderPlacedEventString);

        System.out.println("After sending the event");
        completableFuture
            .exceptionally(ex -> {
                log.error(ex.getMessage());
                ex.printStackTrace();
                return null;
            })
            .thenAccept(result -> System.out.println(result));
    }
}
