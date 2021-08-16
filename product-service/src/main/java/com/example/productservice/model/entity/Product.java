package com.example.productservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int price;
    private String description;
    private int weight;

    public Product(String name, int price, String description, int weight) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.weight = weight;
    }

    public Product fromDetails(ProductDetail productDetails) {
        return Product.builder()
                .id(this.getId())
                .name(this.getName())
                .price(this.getPrice())
                .description(this.getDescription())
                .weight(productDetails.getWeight())
                .build();
    }


}
