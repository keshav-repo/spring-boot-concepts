package com.example.service;

import com.example.model.OrderPlacedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;

    public void sendOrder(OrderPlacedEvent orderPlacedEvent) {
        String orderPlacedEventString = null;
        try {
            orderPlacedEventString = objectMapper.writeValueAsString(orderPlacedEvent);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }

//        CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.sendDefault(String.valueOf(orderPlacedEvent.getOrderId()), orderPlacedEventString);
//
//        System.out.println("After sending the event");
//        completableFuture.whenComplete((result,ex)->{
//            if(ex==null){
//                handleSuccess(orderPlacedEvent);
//            }else {
//                handleFailure(orderPlacedEvent, ex);
//            }
//        });

        Message<String> message = MessageBuilder.withPayload(orderPlacedEventString)
                .setHeader(KafkaHeaders.TOPIC, defaultTopic)
                .setHeader(KafkaHeaders.KEY, orderPlacedEvent.getOrderId())
                .build();
        //  .setHeader(KafkaHeaders.CORRELATION_ID, "")

        CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.send(message);
        try{
          SendResult<String, String> result = completableFuture.get(10, TimeUnit.SECONDS);
            System.out.println("After sending the event");

        }catch (InterruptedException | ExecutionException | TimeoutException exception){
            log.info(exception.getMessage());
            exception.printStackTrace();
        }catch (Exception exception){

        }

    }

    private void handleFailure(OrderPlacedEvent orderPlacedEvent, Throwable ex) {
    }

    private void handleSuccess(OrderPlacedEvent orderPlacedEvent) {
    }
}
