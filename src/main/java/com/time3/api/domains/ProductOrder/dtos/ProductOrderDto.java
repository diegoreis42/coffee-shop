package com.time3.api.domains.ProductOrder.dtos;

import com.time3.api.domains.Products.SizeEnum;

import java.util.UUID;

public record ProductOrderDto (UUID productId, Integer quantity, SizeEnum size){
}
