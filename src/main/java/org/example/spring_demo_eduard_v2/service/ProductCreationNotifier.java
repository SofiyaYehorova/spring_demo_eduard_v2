package org.example.spring_demo_eduard_v2.service;

import org.example.spring_demo_eduard_v2.dto.ProductDto;

public interface ProductCreationNotifier {

    void notify(ProductDto createdProduct);
}
