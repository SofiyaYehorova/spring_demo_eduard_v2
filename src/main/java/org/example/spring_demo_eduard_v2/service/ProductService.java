package org.example.spring_demo_eduard_v2.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring_demo_eduard_v2.entity.Product;
import org.example.spring_demo_eduard_v2.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    @PostConstruct
    public void postConstruct() {
        Product product = new Product();
        product.setName("notebook");
        product.setPrice(4.58);
        product.setTotalCount(190);

        productRepository.save(product);

        log.info("Product saved");
    }
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(String name) {
        return productRepository.findByName(name);
    }
}
