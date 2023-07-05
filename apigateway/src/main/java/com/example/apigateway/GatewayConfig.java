package com.example.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("example_route", r -> r
                    .path("/makemytrip")
                    .uri("https://www.makemytrip.com/"))
            .route("another_route", r -> r
                    .path("/cleartrip")
                    .uri("https://www.cleartrip.com/"))
            .build();
    }
}
