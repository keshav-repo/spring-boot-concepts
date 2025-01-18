package com.example.locking;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@SpringBootApplication
public class DbLockingApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DbLockingApplication.class, args);
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        Product product = Product.builder().name("Iphone").price(100000).build();
        productRepository.save(product);

        // Simulate concurrent access
        Thread thread1 = new Thread(() -> productService.updatePrice(product.getId(), 150000, "Thread-1"));
        Thread thread2 = new Thread(() -> productService.updatePrice(product.getId(), 200000, "Thread-2"));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}

@Repository
interface ProductRepository extends JpaRepository<Product, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdWithLock(@Param("id") Long id);
}

@Service
class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void updatePrice(Long id, double newPrice, String threadName) {
        System.out.println(threadName + " attempting to fetch product with id: " + id);
        Product product = productRepository
                .findByIdWithLock(id)
                .orElseThrow(EntityNotFoundException::new);

        System.out.println(threadName + " locked product: " + product);

        // Simulate some processing
        try {
            Thread.sleep(5000); // Simulate delay to hold the lock
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        product.setPrice(newPrice);
        System.out.println(threadName + " updated price to: " + newPrice);
    }
}

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    private String name;

    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
