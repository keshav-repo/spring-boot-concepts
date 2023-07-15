package com.example.mongodb.repo;

import com.example.mongodb.document.GroceryItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ComponentScan({"com.example.mongodb.dao", "com.example.mongodb.service"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void cleanup(){
        itemRepository.deleteAll();
    }

    @Test
    public void saveGroceryItem() {
        GroceryItem wheat = GroceryItem.builder()
                .category("Grains")
                .quantity(5)
                .name("Wheat")
                .build();
        GroceryItem rice = GroceryItem.builder()
                .category("Grains")
                .quantity(5)
                .name("Rice")
                .build();
        GroceryItem bajra = GroceryItem.builder()
                .category("Grains")
                .quantity(5)
                .name("Bajra")
                .build();
        GroceryItem apple = GroceryItem.builder()
                .category("Fruit")
                .quantity(5)
                .name("Apple")
                .build();
        itemRepository.saveAll(List.of(wheat, rice, bajra, apple));

        Query query = new Query(Criteria.where("category").is("Grains"));
        List<GroceryItem> groceryItems = mongoTemplate.find(query, GroceryItem.class);
        assertTrue(groceryItems.size() == 3);
    }
}
