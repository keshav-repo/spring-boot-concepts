package com.example.mongodb;

import com.example.mongodb.document.*;
import com.example.mongodb.repo.CategoryComponentRepo;
import com.example.mongodb.repo.CustomerRepo;
import com.example.mongodb.repo.ItemRepository;
import com.example.mongodb.repo.OrderRepository;
import com.example.mongodb.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class MongodbApplication implements CommandLineRunner {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryComponentRepo categoryComponentRepo;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepo customerRepo;

    public static void main(String[] args) {
        SpringApplication.run(MongodbApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
         // saveGrocery();

        // categoryDbScript();

        // orderDbScript();
    }

    public void saveGrocery() {
        GroceryItem groceryItem = GroceryItem.builder()
                .name("Wheat")
                .quantity(5)
                .category("Grains")
                .build();
        try {
            itemRepository.save(groceryItem);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void orderDbScript() {
        Customer customer = new Customer("John Doe");
        customerRepo.save(customer);

        Order order = new Order("12345");
        order.setCustomer(customer);

        orderRepository.save(order);

        Order order1 = orderRepository.findById(order.getId()).orElse(null);

        System.out.println(order1);
    }

    @Autowired
    private CategoryService categoryService;

    public void categoryDbScript() {
        // Create root categories
        CategoryComponent fashionCategory = new CategoryGroup("Fashion");
        categoryService.saveCategory(fashionCategory);

        CategoryComponent apparelCategory = new CategoryGroup("Apparel");
        categoryService.saveCategory(apparelCategory);

        CategoryComponent nonApparelCategory = new CategoryGroup("Non-Apparel");
        categoryService.saveCategory(nonApparelCategory);

        CategoryComponent outwearCategory = new CategoryGroup("Outwear");
        categoryService.saveCategory(outwearCategory);

        CategoryComponent bottomCategory = new CategoryGroup("Bottom");
        categoryService.saveCategory(bottomCategory);

        CategoryComponent jacketCategory = new Category("Jacket");
        categoryService.saveCategory(jacketCategory);

        CategoryComponent vestCategory = new Category("Vest");
        categoryService.saveCategory(vestCategory);

        categoryService.addToCategory(fashionCategory, List.of(apparelCategory, nonApparelCategory));

        categoryService.addToCategory(apparelCategory, List.of(outwearCategory, bottomCategory));

        categoryService.addToCategory(outwearCategory, List.of(jacketCategory, vestCategory));
    }
}
