package com.sample.server.SampleApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class SampleApiApplication {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(SampleApiApplication.class, args);
	}

	@GetMapping("/welcome")
	public String welcome(){
		return String.format("Welcome to Spring cloud %s", environment.getProperty("server.port"));
	}
}
