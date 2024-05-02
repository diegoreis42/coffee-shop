package com.time3.api.domains.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.time3.api.domains.ProductOrder.ProductOrder;
import com.time3.api.domains.ProductOrder.dtos.ProductOrderDto;
import com.time3.api.domains.Products.Product;
import com.time3.api.domains.Products.ProductRepository;
import com.time3.api.domains.User.User;
import com.time3.api.domains.User.UserRepository;

import jakarta.transaction.Transactional;

@Component
public class OrderUseCases {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void create(List<ProductOrderDto> productOrders, String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        List<ProductOrder> products = new ArrayList<>();

        for (ProductOrderDto productOrder : productOrders) {
            Optional<Product> product = productRepository.findById(productOrder.productId());

            if (product.isPresent()) {
                ProductOrder newProductOrder = new ProductOrder(
                        productOrder.quantity(),
                        productOrder.size(),
                        product.get(),
                        null);

                products.add(newProductOrder);
            }
        }

        repository
                .save(new Order(user, products, "pending"));
    }

    public Page<Order> getAll(PageRequest page) {
        return repository.findAll(page);
    }

    public Order getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new OrderException.OrderNotFound());
    }
}
