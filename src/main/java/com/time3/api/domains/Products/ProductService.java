package com.time3.api.domains.Products;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.time3.api.domains.Products.dtos.ProductDto;
import com.time3.api.domains.Products.dtos.ProductPresentationDto;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    void save(ProductDto productDto) {
        repository
                .save(new Product(productDto.name(), productDto.description(), productDto.price(), productDto.stock(),
                        productDto.urlImage()));
    }

    void saveProduct(Product product) {
        repository.save(product);
    }

    Optional<Product> findByName(String name) {
        return repository.findByName(name);
    }

    Page<ProductPresentationDto> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(
                product -> new ProductPresentationDto(product.getId(), product.getName(), product.getDescription(),
                        product.getPrice(),
                        product.getStock(), product.getRating(), product.getRatingCount(), product.getUrlImage()));
    }

    ProductPresentationDto findById(UUID id) {
        return repository.findById(id).map(
                product -> new ProductPresentationDto(product.getId(), product.getName(), product.getDescription(),
                        product.getPrice(),
                        product.getStock(), product.getRating(), product.getRatingCount(), product.getUrlImage()))
                .orElseThrow(() -> new ProductException.ProductNotFound(id));
    }

    Product getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ProductException.ProductNotFound(id));
    }
}
