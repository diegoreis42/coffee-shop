package com.time3.api.domains.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.time3.api.domains.Products.dtos.ProductDto;

import jakarta.transaction.Transactional;

@Component
public class ProductUseCases {
    @Autowired
    private ProductRepository repository;

    @Transactional
    public void create(ProductDto productDto) {
        repository.findByName(productDto.name()).ifPresent(product -> {
            throw new ProductException.ProductAlreadyExists();
        });

        repository
                .save(new Product(productDto.name(), productDto.description(), productDto.price(), productDto.stock()));
    }
}
