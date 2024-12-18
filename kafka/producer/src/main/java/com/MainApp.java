package com;

import com.producer.OrderProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MainApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        OrderProducer.start();
    }
}
