package com.time3.api.domains.ProductOrder;

import com.time3.api.domains.Order.Order;
import com.time3.api.domains.Products.Product;
import com.time3.api.domains.Products.SizeEnum;
import com.time3.api.shared.GenericSchema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product-orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductOrder extends GenericSchema {
    @Column
    private Integer quantity;

    @Column
    private SizeEnum size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
