package org.example.spring_demo_eduard_v2.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.spring_demo_eduard_v2.dto.ProductDto;
import org.example.spring_demo_eduard_v2.service.DataStorageService;
import org.example.spring_demo_eduard_v2.service.ProductService;
import org.example.spring_demo_eduard_v2.view.View;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
@Slf4j
public class ProductController {

//   Controller <- DTO -> Service <- Entity -> Repository

//    DTO- data transfer object

//    REST, SOAP, GraphQl......gRPC / messagin

    private final ProductService productService;
    private final DataStorageService dataStorageService;

    @JsonView(View.Public.class)
    @GetMapping("/public/products")
    public ResponseEntity<List<ProductDto>> getPublicProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @JsonView(View.Internal.class)
    @GetMapping("/internal/products")
    public ResponseEntity<List<ProductDto>> getInternalProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    //    @GetMapping("/products/{name}")
//    public ResponseEntity<Product> getProduct(@PathVariable String name) {
//        return ResponseEntity.of(productService.getProduct(name));
//    }
    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto) {
        ProductDto product = productService.createProduct(productDto);
        return ResponseEntity.ok(product);
    }

    @SneakyThrows
    @PostMapping("/files")
    public ResponseEntity<Void> uploadFiles(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        log.info("Received a new file with name: {}", file.getOriginalFilename());
        String data = new String(file.getBytes());
        dataStorageService.pushData(data);
        return ResponseEntity.ok().build();
    }
}
