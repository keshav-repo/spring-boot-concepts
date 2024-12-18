package com.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Grouped;

import java.util.Arrays;
import java.util.Properties;

public class WordCountApplication {

    public static void start() {
        // Step 1: Configure Kafka Streams properties
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        // Step 2: Create a StreamsBuilder instance
        StreamsBuilder builder = new StreamsBuilder();

        // Step 3: Build the topology - define source stream from input topic
        KStream<String, String> textLines = builder.stream("input-topic");

        // Step 4: Process the stream - perform the word count
        KTable<String, Long> wordCounts = textLines
                .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
                .groupBy((key, word) -> word, Grouped.with(Serdes.String(), Serdes.String()))  // Replace Serialized with Grouped
                .count();

        // Step 5: Write the word count results to the output topic
        wordCounts.toStream().to("output-topic", Produced.with(Serdes.String(), Serdes.Long()));

        // Step 6: Build and start the Kafka Streams application
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // Step 7: Add shutdown hook to gracefully stop the application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}

