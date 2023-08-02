## Messaging Queue

### Spring cloud kafka vs spring-kafka
Both Spring Cloud Kafka and Spring Kafka are popular libraries for integrating Apache Kafka with Spring-based applications. The choice between them depends on your specific requirements and the context of your application. Here are some considerations:

**Spring Cloud Kafka:**
- Purpose: Spring Cloud Kafka is part of the Spring Cloud ecosystem and provides higher-level abstractions and integration with Spring Cloud features.
- Ease of Use: Spring Cloud Kafka simplifies the configuration and management of Kafka-based messaging by leveraging Spring Cloud's conventions and abstractions.
- Microservices Support: If you're building microservices using Spring Cloud, Spring Cloud Kafka provides seamless integration with other Spring Cloud components, such as service discovery and circuit breakers.
- Additional Features: Spring Cloud Kafka offers additional features like dynamic topic creation, partitioning strategies, and consumer group management.

**Spring Kafka:**
- Purpose: Spring Kafka is a Spring-based library that provides a direct and low-level integration with Apache Kafka.
- Flexibility and Control: Spring Kafka allows fine-grained control over Kafka's features and provides extensive configuration options.
- Advanced Functionality: If your application requires advanced features like custom serializers/deserializers, producer acknowledgments, or advanced consumer configurations, Spring Kafka offers more flexibility in utilizing these features directly.

In summary, if you are building a microservices architecture using Spring Cloud and prefer higher-level abstractions and streamlined integration with other Spring Cloud components, Spring Cloud Kafka is a good choice. It offers simplicity, convention-based configuration, and additional features tailored for microservices.

On the other hand, if you need fine-grained control over Kafka features and desire direct integration without additional abstractions, Spring Kafka provides more flexibility and control over Apache Kafka's functionality.

Consider your specific requirements, the level of control and abstraction you desire, and the existing architecture and frameworks you are using to determine which library best suits your needs.

### Kafka local setup commands on windows
```
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

.\bin\windows\kafka-server-start.bat .\config\server.properties

Create topic
.\bin\windows\kafka-topics.bat --create --topic orders-placed --bootstrap-server localhost:9092 --replication-factor 2 --partitions 3

Consume from a particular topic 
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic order-event --partition 1  --from-beginning

Delete a topic
.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --delete --topic orders-placed

Consume message from beginning
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic orders-placed --from-beginning

Describe topic
.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --describe --topic orders-placed


```


## Note
- Keep in mind that the number of partitions in the Kafka topic should be greater than or equal to the number of consumers in the same consumer group to ensure efficient load balancing.
- 

#### Topic 
In Apache Kafka, a topic is a category or feed name to which messages are published. It represents a specific stream of data or a subject under which messages are organized. Topics in Kafka are similar to a table in a database or a folder in a file system.

A topic is divided into multiple partitions, which are the underlying storage units in Kafka. Each partition is an ordered and immutable sequence of messages. The partitions allow for horizontal scalability and parallel processing of messages within a topic. Each message within a partition is assigned a unique offset, representing its position in the partition.

When a producer sends a message to a topic, the message is appended to one of the partitions of the topic. The partitioning mechanism determines which partition the message will be assigned to. By default, Kafka uses a round-robin approach for distributing messages across partitions. However, custom partitioning strategies can also be implemented based on specific requirements.

Consumers can subscribe to one or more topics and read messages from the partitions. **Each consumer maintains its own offset for each partition it consumes.** This allows consumers to read messages from specific positions in the topic and enables features like fault tolerance and parallel processing.

Topics provide a **logical organization and segregation of data in Kafka**. They enable the decoupling of producers and consumers, as multiple producers can publish messages to the same topic, and multiple consumers can independently read from the topic based on their needs. Topics are an essential concept in Kafka's publish-subscribe messaging model.

#### High throughput 
High throughput refers to the ability of a system or process to handle and process a large volume of data or requests within a given time frame. It is a measure of how much work a system can perform in a given period. 

#### Fault tolerance
Fault tolerance refers to the ability of a system to continue operating properly and providing its intended functionality in the presence of faults or failures. It is the system's capability to withstand and recover from errors, failures, or unexpected events without significant impact on its performance or availability.

#### Offset
Each message within a partition is assigned a unique identifier called an offset. The offset represents the position of the message within the partition. Consumers can read messages from a specific offset, enabling them to process data sequentially and resume from the last consumed offset.

#### Zookeeper in apache kafka 
Apache ZooKeeper is a centralized open-source coordination service that provides distributed synchronization and coordination for distributed systems. It is used as a key component in Apache Kafka to manage and coordinate the cluster, including maintaining metadata, managing leader election, and handling failover.

In the context of Apache Kafka, ZooKeeper performs the following tasks:

1. Cluster Coordination: ZooKeeper maintains information about the Kafka cluster, including the list of brokers, their metadata (such as broker IDs, hostnames, and ports), and topic configurations. It ensures that all brokers have consistent information about the cluster structure.

2. Leader Election: ZooKeeper facilitates the election of leaders for Kafka partitions. Each partition in Kafka is assigned a leader responsible for handling read and write requests. In the event of a leader failure, ZooKeeper coordinates the election of a new leader for the affected partitions.

3. Broker Registration: Kafka brokers register themselves with ZooKeeper, allowing clients to discover the available brokers and establish connections.

4. Topic Management: ZooKeeper keeps track of the topic configurations, such as the number of partitions, replication factor, and partition assignments. It helps in managing the creation, deletion, and modification of topics.

5. Failover Handling: ZooKeeper monitors the liveness of Kafka brokers. In the event of a broker failure, ZooKeeper triggers the leader election process to select a new leader for the affected partitions.

ZooKeeper ensures that all these operations are performed atomically and consistently across the Kafka cluster. It provides a reliable and distributed coordination framework that allows Kafka to operate in a fault-tolerant and highly available manner.

It's worth noting that starting from Apache Kafka version 2.8.0, ZooKeeper is no longer a mandatory requirement. Kafka has introduced a new metadata management protocol called the Kafka Metadata Quorum (KMQuorum) to replace ZooKeeper for storing and managing metadata. However, prior versions of Kafka still rely on ZooKeeper for coordination and cluster management.

#### Kafka Broker
Kafka brokers are the individual instances or nodes that make up the cluster. Each broker is responsible for handling read and write requests from clients, storing and replicating the topic partitions, and participating in the distributed coordination of the cluster.

#### Kafka cluster
A Kafka cluster refers to a group of Apache Kafka brokers that work together to provide a distributed messaging system. It consists of multiple Kafka brokers running on different machines or nodes, forming a highly scalable and fault-tolerant data processing platform.

#### Partition
In Apache Kafka, a partition is a fundamental unit of data organization within a topic. It represents a linearly ordered sequence of messages, and each topic is divided into one or more partitions. Partitions allow for scalable and parallel processing of data in Kafka.

Key characteristics of partitions in Kafka include:

1. Ordering: Messages within a partition are strictly ordered, meaning the order of messages is preserved. This allows for processing events or messages in a sequential manner.

2. Horizontal Scalability: Kafka topics can be divided into multiple partitions, and each partition can be distributed across different brokers in a Kafka cluster. This allows for horizontal scalability by allowing multiple consumers to process messages in parallel across different partitions.

3. Replication: Each partition can have multiple replicas. Replication ensures fault tolerance and high availability by providing redundant copies of data. Replicas are distributed across different brokers, and if one broker fails, another replica can take over the leadership of the partition.

4. Offset: Each message within a partition is assigned a unique identifier called an offset. The offset represents the position of the message within the partition. Consumers can read messages from a specific offset, enabling them to process data sequentially and resume from the last consumed offset.

Partitioning in Kafka provides several benefits:

a) Scalability: By dividing a topic into multiple partitions, Kafka can handle a higher volume of incoming data and allow for parallel processing across different partitions.

b) Parallel Processing: Consumers can read messages from different partitions concurrently, allowing for parallel processing and increased throughput.

c) Fault Tolerance: Replicating partitions across brokers ensures that if a broker or machine fails, the data remains available from the replicated partitions on other brokers.

d) Order Preservation: Messages within a partition are guaranteed to be processed in the order they were produced, ensuring the integrity of event sequencing.

The number of partitions in a topic is determined during topic creation and can be configured based on the expected workload, desired throughput, and fault tolerance requirements. Proper partitioning is essential for achieving optimal performance and fault-tolerant data processing in Apache Kafka.

#### Partition algorithm used in apache kafka
In Apache Kafka, the default partitioner algorithm used is the DefaultPartitioner, which employs a hash-based approach. However, Kafka also provides additional partitioner algorithms that can be used based on specific requirements. Here are some of the commonly used partitioner algorithms in Kafka:

1. DefaultPartitioner: This is the default partitioner algorithm in Kafka. It uses a hash-based approach to calculate the partition number. The hash value of the message key is computed, and modulo arithmetic is applied to assign the message to a specific partition.

2. RoundRobinPartitioner: This algorithm distributes messages in a round-robin fashion across all available partitions. Each new message is assigned to the next partition in a cyclic manner. This approach ensures an even distribution of messages across partitions and can be useful when you want to balance the load evenly across consumers.

3. Custom Partitioning: Kafka allows you to implement a custom partitioner by implementing the `org.apache.kafka.clients.producer.Partitioner` interface. With a custom partitioner, you have full control over how messages are assigned to partitions based on your specific logic. This allows you to implement complex partitioning strategies based on message content, metadata, or any other criteria.

The choice of partitioning algorithm depends on the specific requirements of your application. The default partitioner is often sufficient for most use cases, providing good load balancing and order preservation. However, in certain scenarios, such as when you need to distribute messages evenly or have specific custom partitioning logic, you can explore the other partitioner options or develop a custom partitioner to suit your needs.

When selecting a partitioner, consider factors such as message distribution, load balancing, scalability, and any specific ordering or grouping requirements of your application.

### Version of apache kafka 
```
zookeeper.version=3.6.3
Kafka version: 3.4.0
```

### Consumer Group 
In Apache Kafka, a consumer group is a concept that allows multiple consumer instances to work together to consume and process messages from one or more Kafka topics. **Consumer groups provide a way to parallelize the consumption of messages and distribute the workload across multiple consumer instances**.

Here are the key uses and benefits of consumer groups in Kafka:

1. **Parallel Processing**: Kafka allows multiple consumer instances to join the same consumer group. **Each consumer instance in the group will be assigned a subset of the partitions of the topic(s) it is consuming**. This enables parallel processing of messages, as each consumer instance can independently consume and process messages from its assigned partitions.

2. **Load Distribution**: Consumer groups enable load distribution across multiple consumer instances. By dividing the topic partitions among the consumer instances, Kafka ensures that each consumer instance receives a fair share of the workload. This allows for scalability and efficient utilization of resources, especially when dealing with high message volumes.

3. **Fault Tolerance**: Consumer groups provide fault tolerance. If one consumer instance within a consumer group fails or becomes unavailable, Kafka will automatically reassign the partitions it was consuming to the remaining consumer instances in the group. This ensures uninterrupted message processing and minimizes downtime.

4. **Message Ordering**: Kafka maintains the order of messages within each partition. When multiple consumer instances are part of the same consumer group, Kafka ensures that each message is consumed and processed by only one consumer instance within the group. This guarantees that the order of messages within each partition is preserved.

5. **Scaling Consumer Instances**: When the workload increases or additional processing capacity is needed, new consumer instances can be added to the consumer group. Kafka will automatically redistribute the partitions among the consumer instances, ensuring the workload is evenly distributed and allowing for seamless scaling without interruption.

6. **Exactly-Once Semantics**: Consumer groups can work in conjunction with the transactional capabilities of Kafka to achieve exactly-once semantics. By enabling idempotent and transactional producers, along with appropriate consumer configurations, Kafka ensures that messages are processed exactly once even in the presence of failures or retries.

Consumer groups are an essential component of building scalable and fault-tolerant systems with Apache Kafka. They provide a mechanism for parallelizing message consumption, load distribution, fault tolerance, and preserving message order within partitions. By leveraging consumer groups, applications can efficiently consume and process high volumes of data from Kafka topics.

### How to send message to a particular partition in a kafka 
Using ProducerRecord
```
public ProducerRecord(String topic, Integer partition, K key, V value) {
}
```

### What is the difference between kafka-streams and apache kafka 
Kafka Streams and Apache Kafka are related but serve different purposes in the Apache Kafka ecosystem.

1. Apache Kafka:
   Apache Kafka is a distributed streaming platform that provides a messaging system for building real-time data pipelines and streaming applications. It is designed to handle high-throughput, fault-tolerant, and scalable data streaming. Kafka acts as a distributed commit log, storing and managing streams of records in a fault-tolerant manner. It provides publish-subscribe messaging, message retention, fault tolerance, and horizontal scalability.

2. Kafka Streams:
   Kafka Streams is a client library that is part of the Apache Kafka project. It allows you to build real-time stream processing applications that consume, process, and produce data from Kafka topics. Kafka Streams provides a high-level stream processing DSL (Domain-Specific Language) that simplifies the development of stream processing applications. It integrates seamlessly with Apache Kafka and provides fault-tolerance, scalability, and stateful stream processing capabilities.

In summary, Apache Kafka is the underlying distributed messaging platform that handles the storage, replication, and distribution of streams of records. It provides durable, fault-tolerant, and scalable event streaming. On the other hand, Kafka Streams is a client library that enables developers to process and analyze the data streams in real-time using the Kafka infrastructure. It allows for building stateful stream processing applications using a high-level DSL.

Kafka Streams can be seen as a higher-level abstraction built on top of Apache Kafka, providing a way to write stream processing applications using the Kafka ecosystem. It simplifies the development of stream processing logic by providing a familiar programming model and integrating seamlessly with the Kafka messaging system.











References
- https://www.interviewbit.com/kafka-interview-questions/#kafka-features
- 


