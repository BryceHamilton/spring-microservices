package com.example.productdetailsservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="product-details")
public interface ProductDetailsRepository extends MongoRepository<ProductDetail, Long> {
}
