package com.example.recommendation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recommendation {
    private String title;
    private String type;
    private List<String> genre;
    private double rating;
    private String thumbnailUrl;
}
