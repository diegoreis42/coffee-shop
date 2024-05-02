package com.time3.api.domains.Order;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.time3.api.domains.Order.dtos.OrderDto;
import com.time3.api.domains.ProductOrder.ProductOrder;
import com.time3.api.domains.ProductOrder.dtos.ProductOrderDto;
import com.time3.api.domains.Products.Product;
import com.time3.api.domains.Products.ProductException;
import com.time3.api.domains.Products.ProductRepository;
import com.time3.api.domains.User.User;
import com.time3.api.domains.User.UserRepository;

import jakarta.transaction.Transactional;

@Component
public class OrderUseCases {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(rollbackOn = Exception.class)
    public void create(List<ProductOrderDto> productOrders, String userEmail) {
        User user = userRepository.getReferenceByEmail(userEmail);

        Set<UUID> productsIds = productOrders.stream().map(ProductOrderDto::productId).collect(Collectors.toSet());

        List<Product> products = productRepository.findAllById(productsIds);

        Map<UUID, Product> productsMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        List<ProductOrder> productOrdersToSave = new ArrayList<>();

        for (ProductOrderDto productOrder : productOrders) {
            Product product = productsMap.get(productOrder.productId());

            if (Objects.isNull(product)) {
                throw new ProductException.ProductNotFound(productOrder.productId());
            }

            if (isProductOutOfStock(product.getStock(), productOrder.quantity())) {
                throw new ProductException.ProductOutOfStock(productOrder.productId());
            }

            ProductOrder newProductOrder = new ProductOrder(productOrder.quantity(), productOrder.size(), product,
                    null);

            productOrdersToSave.add(newProductOrder);
        }

        orderService.save(new Order(user, productOrdersToSave, OrderStatusEnum.PENDING));
    }

    private boolean isProductOutOfStock(BigInteger stock, BigInteger quantity) {
        return stock.compareTo(quantity) < 0;
    }

    public Page<OrderDto> getAll(PageRequest page, String userEmail) {
        User userRef = userRepository.getReferenceByEmail(userEmail);

        return orderService.findAllByUser(page, userRef);
    }

    public OrderDto getById(UUID id, String userEmail) {
        User userRef = userRepository.getReferenceByEmail(userEmail);

        return orderService.findByIdAndUser(id, userRef);
    }

    public void cancelOrder(UUID id, String userEmail) {
        Order order = orderService.findById(id);

        if (isOrderNotFromUser(order, userEmail)) {
            throw new OrderException.OrderNotFound();
        }

        order.setStatus(OrderStatusEnum.CANCELED);
        orderService.save(order);
    }

    private boolean isOrderNotFromUser(Order order, String userEmail) {
        return !order.getUser().getEmail().equals(userEmail);
    }
}
