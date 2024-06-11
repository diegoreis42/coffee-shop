package com.time3.api.domains.Order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.time3.api.configuration.SecurityFilter;
import com.time3.api.configuration.TokenService;
import com.time3.api.domains.ProductOrder.dtos.ProductOrderDto;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderUseCases orderUseCases;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SecurityFilter securityFilter;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody List<ProductOrderDto> productOrders, HttpServletRequest request) {
        String userEmail = tokenService.validateToken(
                securityFilter.recoverToken(request));

        orderUseCases.create(productOrders, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
