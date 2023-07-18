# Testing Kafka Applications

- spring-kafka-test jar contains some useful utilities to assist with testing your applications.
- o.s.kafka.test.utils.KafkaTestUtils provides a number of static helper methods to consume records, retrieve various record offsets, and other
- o.s.kafka.test.utils.KafkaTestUtils also provides some static methods to set up producer and consumer properties.

```java
/**
 * Set up test properties for an {@code <Integer, String>} consumer.
 * @param group the group id.
 * @param autoCommit the auto commit.
 * @param embeddedKafka a {@link EmbeddedKafkaBroker} instance.
 * @return the properties.
 */
public static Map<String, Object> consumerProps(String group, String autoCommit,
                                       EmbeddedKafkaBroker embeddedKafka) { ... }

/**
 * Set up test properties for an {@code <Integer, String>} producer.
 * @param embeddedKafka a {@link EmbeddedKafkaBroker} instance.
 * @return the properties.
 */
public static Map<String, Object> producerProps(EmbeddedKafkaBroker embeddedKafka) { ... }
```
- Starting with version 2.5, the consumerProps method sets the ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to earliest. This is because, in most cases, you want the consumer to consume any messages sent in a test case. 
- The EmbeddedKafkaBroker class has a utility method that lets you consume for all the topics it created
```java
Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testT", "false", embeddedKafka);
DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<Integer, String>(
        consumerProps);
Consumer<Integer, String> consumer = cf.createConsumer();
embeddedKafka.consumeFromAllEmbeddedTopics(consumer);
```
- The KafkaTestUtils has some utility methods to fetch results from the consumer. 
```java
/**
 * Poll the consumer, expecting a single record for the specified topic.
 * @param consumer the consumer.
 * @param topic the topic.
 * @return the record.
 * @throws org.junit.ComparisonFailure if exactly one record is not received.
 */
public static <K, V> ConsumerRecord<K, V> getSingleRecord(Consumer<K, V> consumer, String topic) { ... }

/**
 * Poll the consumer for records.
 * @param consumer the consumer.
 * @return the records.
 */
public static <K, V> ConsumerRecords<K, V> getRecords(Consumer<K, V> consumer) { ... }
```
Example 
```java
ConsumerRecord<Integer, String> received = KafkaTestUtils.getSingleRecord(consumer, "topic");
```
## ContainerProperties 
In Apache Kafka, `ContainerProperties` is a class provided by the Spring Kafka library that represents the properties and configuration for a Kafka message listener container. The container is responsible for receiving and processing messages from Kafka topics.

The `ContainerProperties` class provides various settings and options that define the behavior of the Kafka message listener container, including:

- `topics`: The Kafka topics from which the container should consume messages.
- `messageListener`: The message listener implementation that handles the received messages.
- `ackMode`: The acknowledgement mode for the container, which determines how the offsets are committed.
- `concurrency`: The number of concurrent Kafka message listener threads.
- `errorHandler`: The error handler for handling exceptions that occur during message processing.
- `idleEventInterval`: The interval at which idle event notifications are emitted.
- `idlePartitionEventInterval`: The interval at which idle partition event notifications are emitted.
- `idlePartitionEventInterval` and `idlePartitionEventInterval` can be used to monitor and detect idle consumers and partitions.

These properties can be configured and customized based on your specific requirements. By configuring the `ContainerProperties` and associating them with a Kafka message listener container, you can control the behavior of the container and specify how messages are processed from Kafka topics.

## what is  Kafka message listener container ? 
In Apache Kafka, a message listener container is a component responsible for consuming messages from Kafka topics and delivering them to the registered message listeners for processing. **It acts as a bridge between the Kafka broker and the application code that needs to process the messages.**

The Kafka message listener container provides the following key functionalities:

1. **Message Consumption**: The container interacts with the Kafka broker to fetch messages from the specified topics. It manages the offset tracking, partition assignment, and message retrieval from the Kafka partitions.

2. **Thread Management**: The container manages the execution of message listeners in separate threads. It controls the concurrency level, allowing multiple threads to process messages concurrently.

3. **Error Handling**: The container handles exceptions and errors that occur during message processing. It can be configured with an error handler to define how errors are handled, such as logging, retrying, or stopping the container.

4. **Message Dispatching**: The container receives messages from Kafka and dispatches them to the registered message listeners. Each message listener processes the received message according to the application logic.

By using the Kafka message listener container, you can abstract away the low-level details of interacting with Kafka and focus on implementing the message processing logic in your application. The container simplifies the consumption of Kafka messages, provides concurrency control, and offers error handling capabilities to ensure reliable and efficient message processing.

## DefaultKafkaConsumerFactory 
In Apache Kafka, `DefaultKafkaConsumerFactory` is a class provided by the Spring Kafka library that serves as a factory for creating instances of Kafka consumers. It encapsulates the configuration and setup required to create a Kafka consumer.

The `DefaultKafkaConsumerFactory` class implements the `ConsumerFactory` interface and provides default implementations for creating Kafka consumers with sensible defaults. It abstracts away the complexity of configuring a Kafka consumer manually and provides convenience methods for setting properties such as bootstrap servers, key and value deserializers, and additional consumer configuration.

By using `DefaultKafkaConsumerFactory`, you can create instances of Kafka consumers that are pre-configured with the necessary properties to connect to a Kafka cluster and consume messages from specified topics. It provides a simplified way to create and manage Kafka consumer instances in your application.

Here's an example of how to create a `DefaultKafkaConsumerFactory`:

```java
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ConsumerFactory;

Map<String, Object> consumerProps = new HashMap<>();
consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
```

In this example, a `DefaultKafkaConsumerFactory` is created with the necessary configuration properties such as bootstrap servers and deserializers for the key and value. This factory can then be used to create Kafka consumer instances that are ready to consume messages from the specified Kafka cluster.

## MessageListenerContainer 
In Apache Kafka, `MessageListenerContainer` is an interface provided by the Spring Kafka library that represents a container responsible for message listening and processing. It is used to manage the lifecycle of message listeners and provide a higher-level abstraction for consuming messages from Kafka topics.

The `MessageListenerContainer` interface defines methods for starting, stopping, and managing the state of the container. It also provides methods for setting the message listener, error handler, and other properties related to message consumption.

By implementing the `MessageListenerContainer` interface, you can create a custom container that suits your specific requirements for message processing. Alternatively, Spring Kafka provides several concrete implementations of `MessageListenerContainer` out of the box, such as `KafkaMessageListenerContainer` and `ConcurrentMessageListenerContainer`, which offer different concurrency models and features.

Some key features and responsibilities of a `MessageListenerContainer` include:

1. Lifecycle Management: It handles the lifecycle of the container, including starting and stopping the container and managing the underlying Kafka consumer instances.

2. Message Consumption: The container coordinates the consumption of messages from Kafka topics by controlling the assignment of partitions to the underlying Kafka consumers.

3. Concurrency Control: Depending on the implementation, a `MessageListenerContainer` may support concurrent message processing by managing multiple consumer threads and distributing partitions across them.

4. Error Handling: It provides mechanisms for handling and managing exceptions and errors that occur during message processing, including options for retrying, skipping, or logging errors.

By using a `MessageListenerContainer`, you can efficiently consume and process messages from Kafka topics in a managed and configurable manner, allowing you to focus on the business logic of message processing rather than dealing with low-level Kafka consumer details.

## MessageListener
In Apache Kafka, `MessageListener` is an interface provided by the Spring Kafka library that defines the contract for receiving and processing messages from Kafka topics. It is typically implemented by a message listener component in your application that is responsible for handling incoming messages.

The `MessageListener` interface defines a single method `onMessage()` that is invoked by the Kafka message listener container when a message is received. The `onMessage()` method takes a `ConsumerRecord` object as a parameter, which encapsulates the received message along with its associated metadata.

Here's an example of implementing a `MessageListener` in Java:

```java
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

public class MyMessageListener implements MessageListener<String, String> {

    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        // Process the received message
        String key = record.key();
        String value = record.value();
        String topic = record.topic();
        int partition = record.partition();
        long offset = record.offset();

        // Your custom logic goes here
        System.out.println("Received message: " + value);
    }
}
```

In this example, `MyMessageListener` implements the `MessageListener` interface, and the `onMessage()` method is overridden to define the processing logic for the received message. The `ConsumerRecord` provides access to various properties of the message, such as the key, value, topic, partition, and offset.

By implementing the `MessageListener` interface and providing your custom logic inside the `onMessage()` method, you can define how your application handles incoming messages from Kafka topics.





