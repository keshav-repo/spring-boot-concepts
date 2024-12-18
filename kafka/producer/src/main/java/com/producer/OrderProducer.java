package com.producer;

import java.util.Properties;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Order;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

public class OrderProducer {
    public static void start() {

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9094,localhost:9093");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Set acknowledgment level
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all"); // or "1" or "0"
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "3"); // Set retries
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024)); // Batch size
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "5"); // Linger time
        properties.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, Long.toString(33 * 1024 * 1024)); // Buffer memory
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip"); // Enable compression
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5"); // In-flight requests
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true"); // Idempotence

        // Create a Kafka producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        Random random = new Random();
        ObjectMapper objectMapper = new ObjectMapper();

        int recrodCount = 1_000;

        for (int i = 1; true ; i++) {

            // Message details
            String topic = "order";

            // Create a ProducerRecord with a specific partition
            int partition = (i % 3); // specify the desired partition
            String key = "Order-"+partition;

            Order order = Order.builder()
                    .orderId(random.nextInt(1000) + 1) // random id
                    .quantity(random.nextInt(100) + 1)
                    .productName("Product " + random.nextInt(1000))
                    .build();

            String orderJson = "";
            try {
                orderJson = objectMapper.writeValueAsString(order);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, key, orderJson);

            // Send the record
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        System.err.println("Error occurred while sending message: " + exception.getMessage());
                    } else {
                        System.out.println("Message sent successfully! Topic: " + metadata.topic() +
                                ", Partition: " + metadata.partition() + ", Offset: " + metadata.offset());
                    }
                }
            });
        }

        // Flush and close the producer
       // producer.flush();
       // producer.close();
    }
}
