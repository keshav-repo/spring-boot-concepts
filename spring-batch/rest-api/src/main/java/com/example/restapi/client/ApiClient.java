package com.example.restapi.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ApiClient {

    private final RestTemplate restTemplate;

    public ApiClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<Object> fetchData() {
        try {
            ResponseEntity<Object[]> response = restTemplate.getForEntity("https://api.example.com/data", Object[].class);
            return Arrays.asList(response.getBody());
        }catch (Exception exception){
            exception.printStackTrace();
        }
       return null;
    }
}
