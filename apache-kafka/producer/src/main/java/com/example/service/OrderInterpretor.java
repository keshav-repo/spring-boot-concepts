package com.example.service;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderInterpretor implements ProducerInterceptor<String, String> {

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        return null;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (exception == null) {
            // The record was successfully acknowledged
            System.out.println("Record sent successfully to topic " + metadata.topic()
                    + ", partition " + metadata.partition() + ", offset " + metadata.offset());
        } else {
            // The record failed to be sent and was not acknowledged
            System.err.println("Failed to send record to Kafka: " + exception.getMessage());
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
