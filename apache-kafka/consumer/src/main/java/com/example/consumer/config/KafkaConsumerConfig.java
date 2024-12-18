package com.example.consumer.config;

import com.example.consumer.service.ConsumerListnerCustom;
import com.example.consumer.service.OrderBatchInterpretor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import com.example.consumer.model.OrderPlacedEvent;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConsumerConfig {

    private final String bootstrapServers = "localhost:9092, localhost:9093,localhost:9094";
    private final String groupId = "order-consumer-group";

    @Autowired
    private OrderBatchInterpretor orderBatchInterpretor;

    private Long interval = 6000L;

    private Long maxAttempts = 4L;

    @Bean
    public DefaultErrorHandler errorHandler() {
        BackOff fixedBackOff = new FixedBackOff(interval, maxAttempts);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, e) -> {
            log.info("default error handler");
            System.out.println(consumerRecord);
        }, fixedBackOff);
        errorHandler.addRetryableExceptions(SocketTimeoutException.class, RuntimeException.class);
        errorHandler.addNotRetryableExceptions(NullPointerException.class);
        return errorHandler;
    }

    @Bean
    public ConsumerFactory<String, OrderPlacedEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

      //  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        DefaultKafkaConsumerFactory<String, OrderPlacedEvent> consumerFactory = new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(OrderPlacedEvent.class, false));

     // consumerFactory.addListener(new ConsumerListnerCustom());
        return consumerFactory;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderPlacedEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderPlacedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchInterceptor(orderBatchInterpretor);

        factory.setCommonErrorHandler(errorHandler());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        factory.afterPropertiesSet();
        return factory;
    }
}