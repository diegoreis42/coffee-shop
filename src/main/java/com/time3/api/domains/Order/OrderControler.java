package com.time3.api.domains.Order;

import com.time3.api.domains.Order.dtos.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("orders")
public class OrderControler {

    @Autowired
    private OrderUseCases orderUseCases;

    @PostMapping
    public ResponseEntity create(@RequestBody OrderDto orderDto){
        orderUseCases.create(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
