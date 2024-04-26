package com.time3.api.domains.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.time3.api.domains.Products.ProductUseCases;
import com.time3.api.domains.Products.dtos.ProductDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private ProductUseCases productUseCases;

    @PostMapping("products")
    public ResponseEntity<Void> create(@RequestBody @Valid ProductDto productDto) {
        productUseCases.create(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
