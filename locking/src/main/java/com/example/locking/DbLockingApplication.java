package com.example.locking;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Version;
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
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        Product product2 = Product.builder().name("Samsung Galexy s22").price(90000).build();
        productRepository.save(product2);

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        try {
            // Simulate concurrent access for PESSISTIMIC LOCKING
            CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
                productService.updatePrice(product.getId(), 150000, Thread.currentThread().getName());
            }, executorService).exceptionally((e) -> {
                System.out.println("Exception in thread "+ Thread.currentThread().getName()+" : "+e.getMessage());
                return null;
            });

            CompletableFuture<Void> future4 = CompletableFuture.runAsync(() -> {
                productService.updatePrice(product.getId(), 200000, Thread.currentThread().getName());
            }, executorService).exceptionally((e) -> {
                System.out.println("Exception in thread "+ Thread.currentThread().getName()+" : "+e.getMessage());
                return null;
            });

            List<CompletableFuture<Void>> futures2 = List.of(future3, future4);

            // Combine all results using CompletableFuture.allOf
            CompletableFuture<Void> allOf2 = CompletableFuture.allOf(futures2.toArray(new CompletableFuture[0]));

            allOf2.join();

            System.out.println();
            System.out.println();

            // Simulate concurrent access for OPTIMISTIC LOCKING
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                productService.updatePriceOptimistic(product2.getId(), 150000, Thread.currentThread().getName());
            }, executorService).exceptionally((e) -> {
                System.out.println("Exception in thread "+ Thread.currentThread().getName()+" : "+e.getMessage());
                return null;
            });

            CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
                productService.updatePriceOptimistic(product2.getId(), 200000, Thread.currentThread().getName());
            }, executorService).exceptionally((e) -> {
                System.out.println("Exception in thread "+ Thread.currentThread().getName()+" : "+e.getMessage());
                return null;
            });

            List<CompletableFuture<Void>> futures = List.of(future, future2);

            // Combine all results using CompletableFuture.allOf
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

            allOf.join();
        } catch (Exception e) {
            executorService.shutdown();
        }
    }
}

@Repository
interface ProductRepository extends JpaRepository<Product, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdWithLock(@Param("id") Long id);

    @Transactional
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdWithOptimisticLock(@Param("id") Long id);
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

    @Transactional
    public void updatePriceOptimistic(Long id, double newPrice, String threadName) {
        try {
            System.out.println(threadName + " attempting to fetch product with id: " + id);
            Product product = productRepository
                    .findByIdWithOptimisticLock(id)
                    .orElseThrow(EntityNotFoundException::new);

            System.out.println(threadName + " fetched product: " + product);

            // Simulate some processing
            Thread.sleep(5000); // Simulate delay

            product.setPrice(newPrice);
            productRepository.save(product); // This will trigger version check

            System.out.println(threadName + " successfully updated price to: " + newPrice);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("coming in last cache");
            e.printStackTrace();
        }
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
    private Integer version;

    private String name;

    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
