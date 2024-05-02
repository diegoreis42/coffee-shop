package com.time3.api.domains.Order;

import com.time3.api.domains.ProductOrder.ProductOrder;
import com.time3.api.domains.User.User;
import com.time3.api.shared.GenericSchema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Order extends GenericSchema {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private List<ProductOrder> productOrders;

    private String status;
}
