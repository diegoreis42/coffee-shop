package com.time3.api.domains.Order;

import org.springframework.http.HttpStatus;

import com.time3.api.shared.exception.GlobalException;

public class OrderException extends GlobalException {

    public OrderException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public static class OrderNotFound extends OrderException {
        public OrderNotFound() {
            super("Order not found", HttpStatus.NOT_FOUND);
        }
    }
}
