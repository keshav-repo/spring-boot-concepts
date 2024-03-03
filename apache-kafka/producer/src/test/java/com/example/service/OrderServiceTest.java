package com.example.service;

import com.example.model.OrderPlacedEvent;
import com.example.model.ProductPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EmbeddedKafka(topics = {"orders-placed"}, partitions = 1)
@DirtiesContext
@Slf4j
public class OrderServiceTest {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private Consumer<String, String> consumer;

    private String topic;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        topic = "orders-placed";

        // consumer config
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("test-consumer", "false", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singleton(topic));

        // producer config
//        Map<String, Object> producerProps =
//                KafkaTestUtils.producerProps(embeddedKafkaBroker);
//        ProducerFactory<String, String> pf =
//                new DefaultKafkaProducerFactory<String, String>(producerProps);
//        kafkaTemplate = new KafkaTemplate<>(pf);
//        kafkaTemplate.setDefaultTopic(topic);
    }

    @Test
    @DirtiesContext
    public void testOrderEventProduced() throws JsonProcessingException {
        List<ProductPayload> productPayloadList = new ArrayList<>();
        productPayloadList.add(new ProductPayload(10, 5));
        productPayloadList.add(new ProductPayload(11, 7));
        productPayloadList.add(new ProductPayload(12, 6));

        OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent.builder()
                .orderId("1000")
                .customerId("500")
                .productPurchased(productPayloadList)
                .build();

        orderService.sendOrder(orderPlacedEvent);

        ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(5));
        Assertions.assertTrue(consumerRecords.count() == 1);

        ConsumerRecord orderSentRecord = consumerRecords.iterator().next();
        orderSentRecord.value();
        OrderPlacedEvent orderPlacedEventRecord = objectMapper.readValue(String.valueOf(orderSentRecord.value()), OrderPlacedEvent.class);
        assertEquals(orderPlacedEvent, orderPlacedEventRecord);

    }


}

