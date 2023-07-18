package com.example.consumer.controller;

import com.example.consumer.model.OrderPlacedEvent;
import com.example.consumer.model.ProductPayload;
import com.example.consumer.repo.OrderPlacedEventRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(topics = {"orders-placed"}, partitions = 1)
@DirtiesContext
@Slf4j
public class OrderConsumerTest {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderConsumer orderConsumer;
    @Autowired
    private KafkaListenerEndpointRegistry endpointRegistry;
    @Autowired
    private OrderPlacedEventRepo placedEventRepo;
    @Test
    public void testConsumeOrder() throws Exception {
        // Set up a latch to await the consumed message
        CountDownLatch latch = new CountDownLatch(1);

        // Set up a listener for the consumed message
        MessageListener<String, OrderPlacedEvent> listener = new MessageListener<String, OrderPlacedEvent>() {
            @Override
            public void onMessage(ConsumerRecord<String, OrderPlacedEvent> record) {
                OrderPlacedEvent orderPlacedEvent = record.value();
                assertThat(orderPlacedEvent).isNotNull();
                assertThat(orderPlacedEvent.getOrderId()).isEqualTo("1000");
                latch.countDown();
            }
        };

        ContainerProperties containerProperties = new ContainerProperties("orders-placed");
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(String.valueOf("order-consumer-group"), "true", embeddedKafkaBroker);
        DefaultKafkaConsumerFactory<String, OrderPlacedEvent> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(),
                new JsonDeserializer<>(OrderPlacedEvent.class, false));
        MessageListenerContainer container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        container.setupMessageListener(listener);
        container.start();

        // Create a sample order event
        List<ProductPayload> productPayloadList = new ArrayList<>();
        productPayloadList.add(new ProductPayload(10, 5));
        productPayloadList.add(new ProductPayload(11, 7));
        productPayloadList.add(new ProductPayload(12, 6));
        OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent.builder()
                .orderId("1000")
                .customerId("500")
                .productPurchased(productPayloadList)
                .build();

        // Convert the order event to JSON
        String orderEventJson = objectMapper.writeValueAsString(orderPlacedEvent);
        kafkaTemplate.sendDefault(String.valueOf(orderPlacedEvent.getOrderId()), orderEventJson);

        // Wait for the message to be consumed
        boolean consumed = latch.await(5, TimeUnit.SECONDS);
        assertThat(consumed).isTrue();

        // Stop the message listener container
        container.stop();
    }
}
