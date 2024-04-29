package com.time3.api.domains.Products.dtos;

import java.math.BigInteger;

public record ProductDto(String name, String description, BigInteger price, BigInteger stock,
                String urlImage) {

}
