package com.example;

import com.example.consumer.OrderConsumer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaConsumerExample {

    public static void main(String[] args) {

        int consumerCount = 2;
        ExecutorService executor = Executors.newFixedThreadPool(consumerCount);

        for(int i=0; i<consumerCount; i++){

            String clientId = "consumer-" + i;

            // Set up consumer properties
            Properties properties = new Properties();
            properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092, localhost:9093, localhost:9094");
            properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group 2");

            // properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");
           // properties.setProperty(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, "10");

            properties.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "500000");

            properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

            properties.setProperty(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.RoundRobinAssignor");

            properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "CLIENTID "+i);

            OrderConsumer orderConsumer =  new OrderConsumer(properties, clientId);


            executor.submit(orderConsumer);
        }

        executor.shutdown();
    }
}

