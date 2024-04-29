package com.time3.api.domains.Order;

import com.time3.api.configuration.SecurityFilter;
import com.time3.api.configuration.TokenService;
import com.time3.api.domains.ProductOrder.dtos.ProductOrderDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderControler {

    @Autowired
    private OrderUseCases orderUseCases;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SecurityFilter securityFilter;

    @PostMapping
    public ResponseEntity create(@RequestBody List<ProductOrderDto> productOrders, HttpServletRequest request){
        String userEmail = tokenService.validateToken(
                securityFilter.recoverToken(request));

        orderUseCases.create(productOrders, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
