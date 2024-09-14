package com.example.recommendation;

import com.example.recommendation.model.Recommendation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping
public class RecommendationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecommendationApplication.class, args);
    }

    @GetMapping("/api/recommendations")
    public List<Recommendation> getRecommendation() {
        return Arrays.asList(
                Recommendation.builder()
                        .title("Stranger Things")
                        .type("Series")
                        .genre(List.of("Sci-Fi", "Drama"))
                        .rating(4.8)
                        .thumbnailUrl("https://cdn.netflix.com/thumbnails/stranger-things.jpg")
                        .build(),
                Recommendation.builder()
                        .title("The Witcher")
                        .type("Series")
                        .genre(List.of("Fantasy", "Action"))
                        .rating(4.7)
                        .thumbnailUrl("https://cdn.netflix.com/thumbnails/the-witcher.jpg")
                        .build(),
                Recommendation.builder()
                        .title("Breaking Bad")
                        .type("Series")
                        .genre(List.of("Crime", "Drama"))
                        .rating(4.9)
                        .thumbnailUrl("https://cdn.netflix.com/thumbnails/breaking-bad.jpg")
                        .build()
        );
    }
}
