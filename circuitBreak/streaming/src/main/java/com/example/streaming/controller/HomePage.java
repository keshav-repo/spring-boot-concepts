package com.example.streaming.controller;

import com.example.streaming.model.Recommendation;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class HomePage {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/api/home")
    @CircuitBreaker(name = "recommendation", fallbackMethod = "fallbackMethod")
    public ResponseEntity<?> homePage() {
        String url = "http://localhost:8080/api/recommendations";
        try {
            log.info("calling the recommendation service");
            ResponseEntity<List<Recommendation>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Recommendation>>() {
                    }
            );
            List<Recommendation> recommendations = responseEntity.getBody();
            return ResponseEntity.ok(recommendations);
        }catch (HttpClientErrorException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Service unavailable");
        }
    }
    public ResponseEntity<?> fallbackMethod(Exception e) {
        Recommendation moneyHeist = Recommendation.builder()
                .title("Money Heist")
                .type("Series")
                .genre(List.of("Crime", "Thriller"))
                .rating(4.6)
                .thumbnailUrl("https://cdn.netflix.com/thumbnails/money-heist.jpg")
                .build();
        Recommendation queensGambit = Recommendation.builder()
                .title("The Queen's Gambit")
                .type("Series")
                .genre(List.of("Drama", "Sport"))
                .rating(4.9)
                .thumbnailUrl("https://cdn.netflix.com/thumbnails/queens-gambit.jpg")
                .build();
        Recommendation theCrown = Recommendation.builder()
                .title("The Crown")
                .type("Series")
                .genre(List.of("Historical", "Drama"))
                .rating(4.7)
                .thumbnailUrl("https://cdn.netflix.com/thumbnails/the-crown.jpg")
                .build();
        return ResponseEntity.ok(
                Arrays.asList(
                        moneyHeist, theCrown, queensGambit
                )
        );
    }
}
