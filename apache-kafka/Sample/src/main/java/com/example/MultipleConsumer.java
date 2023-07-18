package com.example;


import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.*;

public class MultipleConsumer {
    public static void main(String[] args) {
        // Set up consumer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");

        // Create Kafka consumers
        int numConsumers = 5; // Set the desired number of consumers
        for (int i = 0; i < numConsumers; i++) {
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

            // Subscribe to the topic(s) you want to consume from
            consumer.subscribe(Collections.singletonList("order-event"));

            // Start consuming messages
            Thread consumerThread = new Thread(() -> {
                try {
                    while (true) {
                        ConsumerRecords<String, String> records = consumer.poll(1000);

                        for (ConsumerRecord<String, String> record : records) {
                            System.out.println("Consumer: " + Thread.currentThread().getName() +
                                    ", Received message: " + record.value() +
                                    ", Topic: " + record.topic() +
                                    ", Partition: " + record.partition() +
                                    ", Offset: " + record.offset());
                        }
                    }
                } finally {
                    // Close the consumer when you're done
                    consumer.close();
                }
            });

            // Start the consumer thread
            consumerThread.start();
        }
    }
}

