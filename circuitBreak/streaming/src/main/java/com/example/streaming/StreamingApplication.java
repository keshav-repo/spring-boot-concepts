package com.example.streaming;

import com.example.streaming.model.Recommendation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping
public class StreamingApplication {

	private RestTemplate restTemplate;

	public StreamingApplication() {
		restTemplate = new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(StreamingApplication.class, args);
	}

	@GetMapping("/api/home")
	public ResponseEntity<?> homePage(){
		String url = "http://localhost:8080/api/recommendations";
		try{
			ResponseEntity<List<Recommendation>> responseEntity = restTemplate.exchange(
					url,
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<Recommendation>>() {}
			);

			List<Recommendation> recommendations = responseEntity.getBody();
			if(recommendations.size()==0){
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No recommendations available at the moment.");
			}
			return ResponseEntity.ok(recommendations);
		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No recommendations available at the moment.");
		}
	}
}
