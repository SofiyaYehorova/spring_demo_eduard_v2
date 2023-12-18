package org.example.spring_demo_eduard_v2.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring_demo_eduard_v2.dto.ProductDto;
import org.example.spring_demo_eduard_v2.entity.Product;
import org.example.spring_demo_eduard_v2.mapper.ProductMapper;
import org.example.spring_demo_eduard_v2.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductCreationNotifier productCreationNotifier;

    public List<ProductDto> getProducts() {
        List<Product> products = productRepository.findAll();
        return productRepository
                .findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }
    public ProductDto createProduct(ProductDto productDto){
        log.info("Creating product: {}", productDto);
        Product product = productMapper.toEntity(productDto);
        Product savedProduct = productRepository.save(product);
        ProductDto createdProduct = productMapper.toDto(savedProduct);

        productCreationNotifier.notify(createdProduct);

        return createdProduct;
    }
}
