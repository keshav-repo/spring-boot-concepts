# Learning from Kafka Documentation 

### 1. RoutingKafkaTemplate
The `RoutingKafkaTemplate` is a feature introduced in Spring for Apache Kafka version 2.8.0. It extends the functionality of the regular `KafkaTemplate` by adding support for dynamic routing of messages to different Kafka topics or partitions based on certain conditions.

With the `RoutingKafkaTemplate`, you can dynamically determine the destination topic or partition for each message before sending it to Kafka. This is useful when you need to route messages to different topics or partitions based on message content, message headers, or any other criteria.

Here's a brief overview of how the `RoutingKafkaTemplate` works:

1. Configuration: You configure the `RoutingKafkaTemplate` by providing a `KafkaTemplate` and a `KafkaRoutingTemplateResolver`.

2. Routing: When you send a message through the `RoutingKafkaTemplate`, it first resolves the target topic or partition using the `KafkaRoutingTemplateResolver`. The resolver inspects the message content, headers, or other attributes and determines the appropriate target topic or partition.

3. Sending: The `RoutingKafkaTemplate` then uses the resolved target topic or partition to send the message using the underlying `KafkaTemplate`.

Here's a basic example of how you can use the `RoutingKafkaTemplate` in a Spring Boot application:

```java
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MessageRoutingService {

    private final RoutingKafkaTemplate routingKafkaTemplate;

    public MessageRoutingService(RoutingKafkaTemplate routingKafkaTemplate) {
        this.routingKafkaTemplate = routingKafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        // Create a Spring Messaging Message with the target topic as a header
        Message<String> kafkaMessage = MessageBuilder.withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        // Send the message using the RoutingKafkaTemplate
        routingKafkaTemplate.send(kafkaMessage);
    }
}
```

In this example, the `MessageRoutingService` uses the `RoutingKafkaTemplate` to send messages. The `sendMessage` method takes a `topic` and `message` as input and creates a Spring Messaging Message with the target topic set as a header. The `RoutingKafkaTemplate` then uses the topic specified in the header to route the message accordingly.

To use the `RoutingKafkaTemplate`, you need to set up the `KafkaTemplate` and `KafkaRoutingTemplateResolver` in your Spring configuration. Additionally, you can customize the resolver to implement your own logic for determining the target topic or partition based on the message contents or other attributes.

Overall, the `RoutingKafkaTemplate` provides a flexible and powerful way to dynamically route messages to different Kafka topics or partitions based on runtime conditions, making it a valuable addition to the Spring for Apache Kafka framework.

### 2. DefaultKafkaProducerFactory
- When not using Transactions, by default, the DefaultKafkaProducerFactory creates a singleton producer used by all clients, as recommended in the KafkaProducer javadocs. 

### 3. threadlocal and producerperthread kafka producer example 
In Kafka, using a separate Kafka producer per thread can be achieved using the `ThreadLocal` pattern. The `ThreadLocal` class in Java allows you to create thread-local variables, where each thread has its own copy of the variable. This pattern ensures that each thread operates with its isolated Kafka producer instance, avoiding any potential thread-safety issues.

Let's create an example of using `ThreadLocal` to manage separate Kafka producers per thread:

1. Create a `KafkaProducerFactory` to create Kafka producer instances:

```java
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerFactory {

    private static final String KAFKA_BROKER_SERVER = "localhost:9092";

    public static KafkaProducer<String, String> createProducer() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER_SERVER);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new KafkaProducer<>(configProps);
    }
}
```

2. Use `ThreadLocal` to manage Kafka producers per thread:

```java
import org.apache.kafka.clients.producer.KafkaProducer;

public class KafkaProducerPerThreadExample {

    private static final ThreadLocal<KafkaProducer<String, String>> threadLocalProducer = ThreadLocal.withInitial(KafkaProducerFactory::createProducer);

    public static void main(String[] args) {
        // Spawn multiple threads and use the KafkaProducer
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                KafkaProducer<String, String> producer = threadLocalProducer.get();
                String topic = "my-topic";
                String message = "Message from Thread-" + Thread.currentThread().getId();

                producer.send(new ProducerRecord<>(topic, message),
                        (metadata, exception) -> {
                            if (exception != null) {
                                System.err.println("Failed to send message: " + exception.getMessage());
                            } else {
                                System.out.println("Message sent successfully. Topic: " + metadata.topic() +
                                        ", Partition: " + metadata.partition() +
                                        ", Offset: " + metadata.offset());
                            }
                        });
            });
            thread.start();
        }
    }
}
```

In this example, we have created a `KafkaProducerFactory` class responsible for creating Kafka producers with the required configuration. We then use a `ThreadLocal` instance named `threadLocalProducer` to store the Kafka producer instance for each thread. The `withInitial` method of `ThreadLocal` ensures that a new producer is created for each thread on its first access.

We then spawn multiple threads, each sending a message using its own Kafka producer instance. The `threadLocalProducer.get()` call inside the thread retrieves the Kafka producer instance specific to that thread.

Using a separate Kafka producer per thread helps avoid synchronization and thread-safety issues. It also provides better performance when multiple threads are concurrently producing messages to Kafka. However, be cautious not to create an excessive number of Kafka producers, as they consume system resources, such as network connections and memory.

Note: In a real-world application, you might want to use a thread pool or other mechanisms for better resource management instead of spawning threads directly as shown in this example.

### 4. Using DefaultKafkaProducerFactory
- Alternatively you can provide Supplier<Serializer> s (starting with version 2.3) that will be used to obtain separate Serializer instances for each Producer.
- https://www.baeldung.com/kafka-custom-serializer
- Starting with version 2.5.10, you can now update the producer properties after the factory is created. This might be useful, for example, if you have to update SSL key/trust store locations after a credentials change. The changes will not affect existing producer instances; call reset() to close any existing producers so that new producers will be created using the new properties. NOTE: You cannot change a transactional producer factory to non-transactional, and vice-versa.
```
void updateConfigs(Map<String, Object> updates);
void removeConfig(String configKey);
```
- 



## References
- https://docs.spring.io/spring-kafka/docs/current/reference/html/#whats-new-part

