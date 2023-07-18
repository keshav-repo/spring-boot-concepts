package com.example;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerMultiple {
    public static void main(String[] args) {
        // Set up producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9094,localhost:9093");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create a Kafka producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // Message details
        String topic = "order-event";

        // Send 10 messages
        for (int i = 0; i < 10; i++) {
            String key = "key_" + i;
            String value = "message_" + i;

            int partition = (i%3);

            // Create a ProducerRecord with a specific partition
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, key, value);

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
        producer.flush();
        producer.close();
    }
}
