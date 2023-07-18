package com.example.consumer.service;

import com.example.consumer.model.OrderPlacedEvent;
import com.example.consumer.repo.OrderPlacedEventRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderPlacedEventRepo orderPlacedEventRepo;

    public int saveEvent(OrderPlacedEvent orderPlacedEvent){
        try{
            orderPlacedEventRepo.save(orderPlacedEvent);
            return orderPlacedEvent.getEventId();
        }catch (Exception exception){
            log.info(exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException("SERVER ERROR");
        }
    }

}
