package com.time3.api.domains.Products;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.time3.api.domains.Products.dtos.ProductDto;
import com.time3.api.domains.Products.dtos.ProductPresentationDto;
import com.time3.api.domains.Products.dtos.ProductRatingDto;

import jakarta.transaction.Transactional;

@Component
public class ProductUseCases {
    @Autowired
    private ProductService service;

    @Transactional
    public void create(ProductDto productDto) {
        service.findByName(productDto.name()).ifPresent(product -> {
            throw new ProductException.ProductAlreadyExists();
        });

        service.save(productDto);
    }

    public Page<ProductPresentationDto> getAll(PageRequest pageRequest) {
        return service.findAll(pageRequest);
    }

    public ProductPresentationDto getById(UUID productId) {
        return service.findById(productId);
    }

    public void rateProduct(UUID productId, ProductRatingDto productRating) {
        Product product = service.getById(productId);

        product.setRating(productRating.rating());
        service.saveProduct(product);
    }
}
