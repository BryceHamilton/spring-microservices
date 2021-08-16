package com.example.productservice.controller;

import com.example.productservice.model.entity.Product;
import com.example.productservice.model.entity.ProductDetail;
import com.example.productservice.respository.ProductRepository;
import com.example.productservice.services.ProductDetailsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductDetailsClient productDetailsClient;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok().body(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findOne(@PathVariable long id) {
        Optional<Product> _product = productRepository.findById(id);
        if (!_product.isPresent()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        ProductDetail productDetails = this.productDetailsClient.getProductDetailsById(id);
        Product product = _product.get().fromDetails(productDetails);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        Product _product = this.productRepository.save(product);
        return new ResponseEntity<>(_product, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product product) {
        Product _product = this.productRepository.save(product);
        return new ResponseEntity<>(_product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable long id) {
        this.productRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
