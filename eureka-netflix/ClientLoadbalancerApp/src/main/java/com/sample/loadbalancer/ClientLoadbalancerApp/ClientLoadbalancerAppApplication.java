package com.sample.loadbalancer.ClientLoadbalancerApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class ClientLoadbalancerAppApplication {

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	public static void main(String[] args) {
		SpringApplication.run(ClientLoadbalancerAppApplication.class, args);
	}

	@GetMapping("/invoke")
	public String invokeService(){
		// Choose a service instance using load balancing
		ServiceInstance serviceInstance = loadBalancerClient.choose("SAMPLE API");

		// Build the target URL for the service
		String targetUrl = serviceInstance.getUri().toString() + "/api/welcome";

		// Make a REST call to the service
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(targetUrl, String.class);

		return "Response from service: " + response;
	}

}
