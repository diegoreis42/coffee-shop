package com.time3.api.domains.Products.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ProductRatingDto(
        @Min(value = 0, message = "Rating não pode ser menor do que zero") @Max(value = 5, message = "Rating não pode ser maior do que cinco") Double rating) {
}
