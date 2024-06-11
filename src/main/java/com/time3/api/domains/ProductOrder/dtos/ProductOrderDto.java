package com.time3.api.domains.ProductOrder.dtos;

import com.time3.api.domains.Products.SizeEnum;

import java.math.BigInteger;
import java.util.UUID;

public record ProductOrderDto (UUID productId, BigInteger quantity, SizeEnum size){
}
