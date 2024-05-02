package com.time3.api.domains.Order;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    PENDING("pending"),
    DELIVERED("delivered"),
    CANCELED("canceled");

    private String status;

    OrderStatusEnum(String status) {
        this.status = status;
    }
}
