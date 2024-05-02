package com.time3.api.domains.Order;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.time3.api.domains.Order.dtos.OrderDto;
import com.time3.api.domains.ProductOrder.dtos.ProductOrderDto;
import com.time3.api.domains.User.User;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    OrderDto findByIdAndUser(UUID id, User user) {
        return repository.findByIdAndUser(id, user).map(order -> new OrderDto(order.getId(),
                order.getProductOrders().stream()
                        .map(productOrder -> new ProductOrderDto(productOrder.getProduct().getId(),
                                productOrder.getQuantity(), productOrder.getSize()))
                        .collect(Collectors.toList()),
                order.getStatus())).orElseThrow(() -> new OrderException.OrderNotFound());
    }

    Page<OrderDto> findAllByUser(PageRequest page, User user) {
        return repository.findAllByUser(page, user).map(order -> new OrderDto(order.getId(),
                order.getProductOrders().stream()
                        .map(productOrder -> new ProductOrderDto(productOrder.getProduct().getId(),
                                productOrder.getQuantity(), productOrder.getSize()))
                        .collect(Collectors.toList()),
                order.getStatus()));
    }

    void save(Order order) {
        repository.save(order);
    }

    Order findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new OrderException.OrderNotFound());
    }
}
