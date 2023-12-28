package org.example.spring_demo_eduard_v2.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring_demo_eduard_v2.dto.ProductDto;
import org.example.spring_demo_eduard_v2.dto.ReviewDto;
import org.example.spring_demo_eduard_v2.entity.Product;
import org.example.spring_demo_eduard_v2.entity.ProductReview;
import org.example.spring_demo_eduard_v2.entity.Review;
import org.example.spring_demo_eduard_v2.mapper.ProductMapper;
import org.example.spring_demo_eduard_v2.mapper.ReviewMapper;
import org.example.spring_demo_eduard_v2.repository.ProductRepository;
import org.example.spring_demo_eduard_v2.repository.ProductReviewRepository;
import org.example.spring_demo_eduard_v2.services.notifier.ProductCreationNotifier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Qualifier("wsNotifier")
    private final ProductCreationNotifier productCreationNotifier;
    private final ProductReviewRepository productReviewRepository;
    private final ReviewMapper reviewMapper;

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

    public List<ReviewDto> getReviews(Integer productId){
        if (!productRepository.existsById(productId)) {
            throw new NoSuchElementException("product with id {} does not exist");
        }
        return productReviewRepository
//                .findByProductId(productId)
                .get(productId)
                .stream()
                .flatMap(productReview -> productReview.getReviews().stream())
                .map(reviewMapper::mapToDto)
                .toList();
    }

    public ReviewDto addReview(Integer productId, ReviewDto reviewDto){
        if (!productRepository.existsById(productId)) {
            throw new NoSuchElementException("product with id {} does not exist");
        }

        Review review = reviewMapper.map(reviewDto);
        review.setTimestamp(LocalDateTime.now());

        ProductReview productReview = productReviewRepository
//                .findByProductId(productId)
                .get(productId)
                .orElseGet(() -> new ProductReview()
                        .setProductId(productId));
        if(productReview.getReviews() !=null){
            productReview.getReviews().add(review);
        }
        else {
            productReview.setReviews(List.of(review));
        }
        productReviewRepository.save(productReview);

        return reviewMapper.mapToDto(review);
    }
}
