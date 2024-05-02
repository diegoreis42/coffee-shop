package com.time3.api.domains.Order;

import java.util.List;
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

    @GetMapping
    public ResponseEntity<Page<Order>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return ResponseEntity.ok().body(orderUseCases.getAll(PageRequest.of(page, size)));
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(orderUseCases.getById(id));
    }
}
