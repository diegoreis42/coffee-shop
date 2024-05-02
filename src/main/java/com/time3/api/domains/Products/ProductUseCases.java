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
    private ProductRepository repository;

    @Transactional
    public void create(ProductDto productDto) {
        repository.findByName(productDto.name()).ifPresent(product -> {
            throw new ProductException.ProductAlreadyExists();
        });

        repository
                .save(new Product(productDto.name(), productDto.description(), productDto.price(), productDto.stock(),
                        productDto.urlImage()));
    }

    public Page<ProductPresentationDto> getAll(PageRequest pageRequest) {
        Page<Product> products = repository.findAll(pageRequest);

        return products.map(
                product -> new ProductPresentationDto(product.getId(), product.getName(), product.getDescription(),
                        product.getPrice(),
                        product.getStock(), product.getRating(), product.getRatingCount(), product.getUrlImage()));
    }

    public ProductPresentationDto getById(UUID productId) {
        Product product = repository.findById(productId).orElseThrow(() -> new ProductException.ProductNotFound(productId));

        return new ProductPresentationDto(product.getId(), product.getName(), product.getDescription(),
                product.getPrice(),
                product.getStock(), product.getRating(), product.getRatingCount(), product.getUrlImage());
    }

    public void rateProduct(UUID productId, ProductRatingDto productRating) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductException.ProductNotFound(productId));

        product.setRating(productRating.rating());
        repository.save(product);
    }
}
