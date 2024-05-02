package com.time3.api.domains.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.time3.api.domains.ProductOrder.dtos.ProductOrderDto;
import com.time3.api.domains.Products.Product;
import com.time3.api.domains.Products.ProductException;
import com.time3.api.domains.Products.ProductRepository;
import com.time3.api.domains.Products.SizeEnum;
import com.time3.api.domains.User.User;
import com.time3.api.domains.User.UserRepository;

@ExtendWith(MockitoExtension.class)
public class OrderUseCasesTest {

    @Mock
    private OrderService orderService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderUseCases orderUseCases;

    private User user;
    private Product product;
    private UUID productId;
    private UUID orderId;
    private Order order;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("user@example.com");
        product = new Product("Product", "Description", BigInteger.valueOf(10), BigInteger.valueOf(10), "urlImage");
        orderId = UUID.randomUUID();
        order = new Order(user, null, OrderStatusEnum.PENDING);
    }

    @Test
    public void shouldCreateOrderSuccessfully() {
        List<ProductOrderDto> productOrders = Arrays
                .asList(new ProductOrderDto(productId, new BigInteger("5"), SizeEnum.M));

        when(productRepository.findAllById(Collections.singleton(productId)))
                .thenReturn(Collections.singletonList(product));
        orderUseCases.create(productOrders, user.getEmail());

        verify(orderService, times(1)).save(any(Order.class));
    }

    @Test
    public void shouldThrowProductNotFoundExceptionIfProductDoesNotExist() {
        UUID wrongId = UUID.randomUUID();
        List<ProductOrderDto> productOrders = Arrays.asList(new ProductOrderDto(wrongId, new BigInteger("1"),
                SizeEnum.M));

        when(productRepository.findAllById(Collections.singleton(wrongId))).thenReturn(Collections.emptyList());

        assertThrows(ProductException.ProductNotFound.class, () -> {
            orderUseCases.create(productOrders, user.getEmail());
        });
    }

    @Test
    public void shouldThrowProductOutOfStockExceptionIfQuantityExceedsStock() {
        List<ProductOrderDto> productOrders = Arrays
                .asList(new ProductOrderDto(productId, new BigInteger("20"), SizeEnum.M));
        List<Product> products = Collections.singletonList(product);

        when(productRepository.findAllById(Collections.singleton(productId))).thenReturn(products);

        assertThrows(ProductException.ProductOutOfStock.class, () -> {
            orderUseCases.create(productOrders, user.getEmail());
        });
    }

    @Test
    public void shouldCancelOrderSuccessfully() {
        when(orderService.findById(orderId)).thenReturn(order);

        orderUseCases.cancelOrder(orderId, user.getEmail());

        verify(orderService, times(1)).save(order);
        assertEquals(OrderStatusEnum.CANCELED, order.getStatus());
    }

    @Test
    public void shouldThrowExceptionIfOrderNotBelongsUser() {
        user.setEmail("another@example.com");
        order.setUser(user);

        when(orderService.findById(orderId)).thenReturn(order);

        assertThrows(OrderException.OrderNotFound.class, () -> {
            orderUseCases.cancelOrder(orderId, "user@example.com");
        });
    }

}
