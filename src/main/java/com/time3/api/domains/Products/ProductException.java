package com.time3.api.domains.Products;

import org.springframework.http.HttpStatus;

import com.time3.api.shared.exception.GlobalException;

public class ProductException extends GlobalException {
    public ProductException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class ProductAlreadyExists extends ProductException {
        public ProductAlreadyExists() {
            super("Produto já cadastrado", HttpStatus.CONFLICT);
        }
    }

}
