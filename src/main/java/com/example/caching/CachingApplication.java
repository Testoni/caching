package com.example.caching;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableCaching
public class CachingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CachingApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(ProductService productService) {
        return args -> {
            System.out.println("\n\n\n\n\n\n\n\n\n");
            System.out.println("ID: 1: " + productService.getById(1L));
            System.out.println("ID: 2: " + productService.getById(2L));
            System.out.println("ID: 1: " + productService.getById(1L));
            System.out.println("ID: 1: " + productService.getById(1L));
            System.out.println("ID: 1: " + productService.getById(1L));
        };
    }

}

record Product(Long id, String name, String description) implements Serializable {

}

@Service
class ProductService {
    Map<Long, Product> products = new HashMap<>() {
        {
            put(1L, new Product(1L, "A", "Test A"));
            put(2L, new Product(2L, "B", "Test B"));
            put(3L, new Product(3L, "C", "Test C"));
            put(4L, new Product(4L, "D", "Test D"));
            put(5L, new Product(5L, "E", "Test E"));
        }
    };

    @Cacheable("products")
    Product getById(Long id) {
        System.out.println("Searching product...");
        simulateLatency();
        return products.get(id);
    }

    @CacheEvict(value = "products", allEntries = true)
    void addProduct(Product product) {
        products.put((products.size() + 1L), product);
    }

    private void simulateLatency() {
        try {
            long time = 1000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}