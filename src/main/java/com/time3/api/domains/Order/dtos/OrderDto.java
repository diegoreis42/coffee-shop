package com.time3.api.domains.Order.dtos;

import java.util.List;
import java.util.UUID;

import com.time3.api.domains.Order.OrderStatusEnum;
import com.time3.api.domains.ProductOrder.dtos.ProductOrderDto;

public record OrderDto(UUID id, List<ProductOrderDto> productOrders, OrderStatusEnum status) {

}
