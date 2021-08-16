package com.example.productservice.services;

import com.example.productservice.model.entity.ProductDetail;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private final WebClient.Builder webClientBuilder;

    private ProductDetail getProductDetailsCall(long productId) {
        return this.webClientBuilder
                .build()
                .get()
                .uri("http://product-details-service/product-details/" + productId)
                .retrieve()
                .bodyToMono(ProductDetail.class)
                .block();
    }

    private ProductDetail getFallbackProductDetails(Throwable throwable) {
        return new ProductDetail(2, 1, "fallback green");
    }



    public ProductDetail getProductDetailsById(long productId) {

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(20)
                .waitDurationInOpenState(Duration.ofMillis(10000))
                .permittedNumberOfCallsInHalfOpenState(2)
                .slidingWindowSize(5)
                .minimumNumberOfCalls(5)
                .recordExceptions(IOException.class, TimeoutException.class)
                .build();

    // Create a CircuitBreakerRegistry with a custom global configuration
        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);

        CircuitBreaker circuitBreaker = circuitBreakerRegistry
                .circuitBreaker("productDetailsService");

        Supplier<ProductDetail> supplier = CircuitBreaker.decorateSupplier(circuitBreaker, () -> this.getProductDetailsCall(productId));

        return Try.ofSupplier(supplier)
                .recover(this::getFallbackProductDetails).get();


        /*
        CircuitBreaker circuitBreakerDefault = CircuitBreaker.ofDefaults("productDetailsService");
        Supplier<ProductDetail> supplier = CircuitBreaker.decorateSupplier(circuitBreakerDefault, this::getProductDetailsCall);
        Try<ProductDetail> result = Try.ofSupplier(supplier);
        if (result.isSuccess()) {
            return result.get();
        }
        else {
            return this.getFallbackProductDetails();
        }
        */

    }

}
