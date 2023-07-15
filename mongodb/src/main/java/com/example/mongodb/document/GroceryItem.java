package com.example.mongodb.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("groceryitems")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroceryItem {
    @Id
    private String id;
    private String name;
    private int quantity;
    private String category;
}
