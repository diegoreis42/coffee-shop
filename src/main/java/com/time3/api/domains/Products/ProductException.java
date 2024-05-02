package com.time3.api.domains.Products;

import java.util.UUID;

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

    public static class ProductNotFound extends ProductException {
        public ProductNotFound(UUID productId) {
            super(String.format("Produto com id: %s não foi encontrado", productId), HttpStatus.NOT_FOUND);
        }
    }

    public static class ProductOutOfStock extends ProductException {
        public ProductOutOfStock(UUID productId) {
            super(String.format("Produto com id: %s está fora de estoque", productId), HttpStatus.BAD_REQUEST);
        }
    }

}
