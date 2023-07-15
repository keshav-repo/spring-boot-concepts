package com.example.mongodb.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Order {
    @Id
    private String id;
    private String orderNumber;

    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    @DBRef
    private Customer customer;

}
