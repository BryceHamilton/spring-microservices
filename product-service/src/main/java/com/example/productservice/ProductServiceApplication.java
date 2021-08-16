package com.example.productservice;

import com.example.productservice.model.entity.Product;
import com.example.productservice.respository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}

	/*
	@Bean
	public CommandLineRunner demo(ProductRepository productRepository) {
		return (args) -> {
			Product prod1 = new Product("hat", 123, "nice and comfy", 12);
			Product prod2 = new Product("shirt", 456, "good fit", 14);
			Product prod3 = new Product("jacket", 768, "for when it's chilly", 3);
			List<Product> products = new ArrayList<>(List.of(prod1, prod2, prod3));
			productRepository.saveAll(products);
		};
	}
	*/
}
