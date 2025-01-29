package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentServiceApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApp.class);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("In PaymentServiceApp");
    }
}
