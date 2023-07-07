package com.example.AppUsingConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableAutoConfiguration
public class AppUsingConfigApplication implements CommandLineRunner {

	@Autowired
	private Environment environment;

	@Value("${user.role}")
	private String role;

	public static void main(String[] args) {
		SpringApplication.run(AppUsingConfigApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(String.format("user role is %s", role));

		System.out.println("server port is "+ environment.getProperty("server.port"));


	}
}
