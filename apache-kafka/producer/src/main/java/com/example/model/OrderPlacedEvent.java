package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderPlacedEvent {
    private String orderId;
    private String customerId;
    private List<ProductPayload> productPurchased;
}
