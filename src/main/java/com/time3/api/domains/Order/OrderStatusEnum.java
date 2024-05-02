package com.time3.api.domains.Order;

public enum OrderStatusEnum {
    PENDING("pending"),
    DELIVERED("delivered"),
    CANCELED("canceled");

    private final String status;

    OrderStatusEnum(String status) {
        this.status = status;
    }
}
