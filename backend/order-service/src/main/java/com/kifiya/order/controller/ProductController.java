package com.kifiya.order.controller;

import com.kifiya.order.controller.domains.ProductRequest;
import com.kifiya.order.models.Product;
import com.kifiya.order.models.ProductCategory;
import com.kifiya.order.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
class ProductController {
    private final ProductRepository productRepository;
    public ProductController(ProductRepository productRepository) { this.productRepository = productRepository; }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> list = productRepository.findAll();
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request) {

        Product product = new Product();
        product.setId(request.id());
        product.setName(request.name());
        product.setCategory(ProductCategory.valueOf(request.category()));
        product.setPrice(request.price());
        product.setStock(request.stock());
        Product saved = productRepository.saveAndFlush(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
