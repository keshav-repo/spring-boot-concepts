package com.example.consumer.service;

import com.example.consumer.model.OrderPlacedEvent;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.core.ConsumerFactory;


public class ConsumerListnerCustom implements ConsumerFactory.Listener<String, OrderPlacedEvent> {
    @Override
    public void consumerAdded(String id, Consumer<String, OrderPlacedEvent> consumer) {
        ConsumerFactory.Listener.super.consumerAdded(id, consumer);
    }

    @Override
    public void consumerRemoved(String id, Consumer<String, OrderPlacedEvent> consumer) {
        ConsumerFactory.Listener.super.consumerRemoved(id, consumer);
    }
}
