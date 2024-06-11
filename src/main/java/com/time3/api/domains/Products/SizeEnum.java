package com.time3.api.domains.Products;

import lombok.Getter;

@Getter
public enum SizeEnum {
    S("little"),
    M("medium"),
    L("large");

    private String size;

    SizeEnum(String size) {
        this.size = size;
    }
}
