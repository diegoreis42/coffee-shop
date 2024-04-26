package com.time3.api.domains.Order.dtos;

import com.time3.api.domains.ProductOrder.dtos.ProductOrderDto;

import java.util.List;

public record OrderDto (String userEmail, List<ProductOrderDto> productOrders) {
}
