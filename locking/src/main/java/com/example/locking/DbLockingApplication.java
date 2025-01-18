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

        productService.updatePrice(product.getId(), 150000);
    }
}

@Repository
interface ProductRepository extends JpaRepository<Product, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findById(Long id);
}

@Service
class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void updatePrice(Long id, double newPrice) {
		Product product = productRepository
				.findById(id)
				.orElseThrow(EntityNotFoundException::new);
		product.setPrice(newPrice);
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
