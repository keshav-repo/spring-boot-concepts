package com.example.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;

public class OrderProducerListener implements ProducerListener<String, String> {
    @Override
    public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
        ProducerListener.super.onSuccess(producerRecord, recordMetadata);
    }

    @Override
    public void onError(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata, Exception exception) {
        ProducerListener.super.onError(producerRecord, recordMetadata, exception);
    }
}
