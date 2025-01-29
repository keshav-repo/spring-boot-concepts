package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@SpringBootApplication
@Slf4j
public class OrderServiceApp implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, String> template;

    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("in OrderServiceApp");

        List<Order> orderList = randomObject();
        for (Order order : orderList) {
            template.send("test-topic", objectMapper.writeValueAsString(order));
        }
    }

    private List<Order> randomObject() {
        Order order1 = Order.builder()
                .orderId("ORD12345")
                .customerName("John Doe")
                .productName("Wireless Mouse")
                .quantity(2)
                .price(29.99)
                .build();

        Order order2 = Order.builder()
                .orderId("ORD67890")
                .customerName("Alice Smith")
                .productName("Mechanical Keyboard")
                .quantity(1)
                .price(89.99)
                .build();

        Order order3 = Order.builder()
                .orderId("ORD54321")
                .customerName("Robert Brown")
                .productName("Gaming Headset")
                .quantity(3)
                .price(59.99)
                .build();

        Order order4 = Order.builder()
                .orderId("ORD98765")
                .customerName("Emma Johnson")
                .productName("USB-C Hub")
                .quantity(5)
                .price(19.99)
                .build();
        return List.of(order1, order2, order3, order4);
    }
}


@Configuration
class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Value("${spring.kafka.producer.client-id}")
    private String clientId;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}