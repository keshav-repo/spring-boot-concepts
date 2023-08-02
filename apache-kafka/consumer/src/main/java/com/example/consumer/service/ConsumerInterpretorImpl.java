package com.example.consumer.service;

import com.example.consumer.model.OrderPlacedEvent;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConsumerInterpretorImpl implements ConsumerInterceptor<String, OrderPlacedEvent> {
    @Override
    public ConsumerRecords<String, OrderPlacedEvent> onConsume(ConsumerRecords<String, OrderPlacedEvent> consumerRecords) {

        int recordCount = consumerRecords.count();
        System.out.println("record count is "+recordCount);

        return null;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {
        System.out.println("on coommit map");
        System.out.println(map);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
