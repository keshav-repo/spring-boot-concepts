package com.example.config;

import com.example.service.OrderInterpretor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String server;
    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;

    @Autowired
    private OrderInterpretor orderInterpretor;
    @Bean
    public ProducerFactory<String, String> kafkaProducerFactory() {
        Map<String, Object> producerProperties = new HashMap<>();

        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(producerProperties);


       // factory.setProducerPerThread(true);

        return factory;
    }
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory());
        kafkaTemplate.setDefaultTopic(defaultTopic);
        kafkaTemplate.setProducerInterceptor(orderInterpretor);
        return kafkaTemplate;
    }

    @Bean
    public NewTopic topic4() {
        return TopicBuilder.name("topic")
                .build();
    }
}
