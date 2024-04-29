package com.time3.api.domains.Products.dtos;

import java.math.BigInteger;
import java.util.UUID;

public record ProductPresentationDto(UUID id, String name, String description, BigInteger price, BigInteger stock,
                Double rating, BigInteger ratingCount,
                String urlImage) {

}
