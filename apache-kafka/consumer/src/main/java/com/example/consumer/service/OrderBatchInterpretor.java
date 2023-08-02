package com.example.consumer.service;

import com.example.consumer.model.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.listener.BatchInterceptor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderBatchInterpretor implements BatchInterceptor<String, OrderPlacedEvent> {
    @Override
    public ConsumerRecords<String, OrderPlacedEvent> intercept(ConsumerRecords<String, OrderPlacedEvent> records, Consumer<String, OrderPlacedEvent> consumer) {
        return null;
    }

    @Override
    public void success(ConsumerRecords<String, OrderPlacedEvent> records, Consumer<String, OrderPlacedEvent> consumer) {
        BatchInterceptor.super.success(records, consumer);
        long recordsCo = records.count();
        log.info("record processed "+recordsCo);
    }
}
