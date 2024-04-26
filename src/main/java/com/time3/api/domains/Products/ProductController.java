package com.time3.api.domains.Products;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.time3.api.domains.Products.dtos.ProductPresentationDto;
import com.time3.api.domains.Products.dtos.ProductRatingDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductUseCases useCases;

    @GetMapping
    public ResponseEntity<Page<ProductPresentationDto>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return ResponseEntity.ok().body(useCases.getAll(PageRequest.of(page, size)));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductPresentationDto> getMethodName(@PathVariable(name = "id") UUID productId) {
        return ResponseEntity.ok().body(useCases.getById(productId));
    }

    @PostMapping("{id}/rate")
    public ResponseEntity<Void> postMethodName(@PathVariable(name = "id") @Valid UUID productId,
            @RequestBody @Valid ProductRatingDto productRating) {
        useCases.rateProduct(productId, productRating);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
