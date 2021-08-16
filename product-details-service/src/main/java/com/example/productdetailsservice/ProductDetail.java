package com.example.productdetailsservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
    @Id
    private long id;
    private int weight;
    private String color;
}
