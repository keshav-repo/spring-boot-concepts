package com.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class OrderConsume {

    public static void start() {

        List<String> list = new CopyOnWriteArrayList<>();

        // Set up consumer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");

        properties.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "500000");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.RoundRobinAssignor");

        // Disable automatic offset committing
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        // Create Kafka consumers
        int numConsumers = 3; // Set the desired number of consumers
        for (int i = 0; i < numConsumers; i++) {
            properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "CLIENTID "+i);
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

            // Subscribe to the topic(s) you want to consume from
            consumer.subscribe(Collections.singletonList("order"));

            // Start consuming messages
            Thread consumerThread = new Thread(() -> {
                try {
                    while (true) {
                        ConsumerRecords<String, String> records = consumer.poll(100);

                        for (ConsumerRecord<String, String> record : records) {
                            System.out.println("Consumer: " + Thread.currentThread().getName() +
                                    ", Received message: " + record.value() +
                                    ", Topic: " + record.topic() +
                                    ", Partition: " + record.partition() +
                                    ", Offset: " + record.offset());

                            list.add(record.value());
                        }
                        consumer.commitAsync(); // or .commitSync()

                    }
                }catch (Exception exception){
                    System.out.println("some exception "+ exception.getLocalizedMessage());
                    exception.printStackTrace();
                }
                finally {
                    // Close the consumer when you're done
                    consumer.close();
                }
            });

            // Start the consumer thread
            consumerThread.start();
        }
    }
}
