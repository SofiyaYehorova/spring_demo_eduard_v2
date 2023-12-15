package org.example.spring_demo_eduard_v2.mapper;

import org.example.spring_demo_eduard_v2.dto.ProductDto;
import org.example.spring_demo_eduard_v2.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto toDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .totalCount(product.getTotalCount())
                .build();
    }

    public Product toEntity(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setTotalCount(productDto.getTotalCount());
        return product;
    }
}
