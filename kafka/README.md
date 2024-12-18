```shell
# producer
java -Dprocname=KafkaProducer -Xms200m -Xmx400m -XX:MaxMetaspaceSize=75m -XX:MetaspaceSize=56m -XX:MaxRAM=375m  -jar /Users/keshavkumar/learn24/spring-boot-concepts/kafka/producer/target/kafka-producer-1.0-SNAPSHOT.jar

# consumer app
java -Dprocname=KafkaConsumer  -Xms300m -Xmx2g -XX:MaxMetaspaceSize=75m -XX:MetaspaceSize=56m -XX:MaxRAM=375m -Djava.rmi.server.hostname=localhost -jar /Users/keshavkumar/learn24/spring-boot-concepts/kafka/consumer/target/kafka-consumer-1.0-SNAPSHOT.jar

```

```shell
java -Dprocname=KafkaProducer -Xms512m -Xmx1g -XX:MaxMetaspaceSize=75m -XX:MetaspaceSize=56m -XX:MaxRAM=375m -jar /Users/keshavkumar/learn24/spring-boot-concepts/kafka/producer/target/kafka-producer-1.0-SNAPSHOT.jar

```

1. Xms200m: minimum initiali memory of 200 MB, 
2. Xmx500g: maximum memory of 5 gb 
3. -XX:MaxMetaspaceSize: max  memory 
4. XX:MetaspaceSize=56m : Intial size of metaspace
5. -XX:MaxRAM=375m : Specify ram size 
6. -XX:+UseParallelGC : 
