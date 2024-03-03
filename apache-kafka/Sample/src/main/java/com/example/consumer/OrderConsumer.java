package com.example.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class OrderConsumer implements Runnable{
    private KafkaConsumer<String, String> consumer;
    public OrderConsumer(Properties properties, String clientId) {
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        consumer = new KafkaConsumer<>(properties);
    }
    @Override
    public void run() {
        consumer.subscribe(Collections.singletonList("orders"));
        // consumer.seekToBeginning(consumer.assignment());



        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                String consumerId = consumer.groupMetadata().memberId();
                System.out.println("Consumer ID: " + consumerId);
                System.out.println(consumer.groupMetadata());
                System.out.println("size "+records.count());
//                for (ConsumerRecord<String, String> record : records) {
//                    System.out.println("Received message: " + record.value() +
//                            ", Topic: " + record.topic() +
//                            ", Partition: " + record.partition() +
//                            ", Offset: " + record.offset());
//                }

            }
        } finally {
            System.out.println("After receiving all records");
            consumer.close();
        }
    }
}
