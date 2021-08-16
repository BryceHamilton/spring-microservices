package com.example.productservice.services;


import com.example.productservice.model.entity.ProductDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-details-service")
public interface ProductDetailsClient {
    @GetMapping("/product-details/{id}")
    public ProductDetail getProductDetailsById(@PathVariable long id);
}
