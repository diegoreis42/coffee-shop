package com.time3.api.domains.Order;

import com.time3.api.domains.Order.dtos.OrderDto;
import com.time3.api.domains.ProductOrder.dtos.ProductOrderDto;
import com.time3.api.domains.Products.Product;
import com.time3.api.domains.Products.ProductRepository;
import com.time3.api.domains.User.User;
import com.time3.api.domains.User.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrderUseCases {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void create(OrderDto orderDto) {
        User user = userRepository.findByEmail(orderDto.userEmail());

        List<Product> products = 

        for( ProductOrderDto productOrder : orderDto.productOrders() ) {
            Optional<Product> product = productRepository.findById(productOrder.productId());

            if( product.isPresent() ) {}

        }



        repository
                .save(new Order(user));
    }
}
