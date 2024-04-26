package com.time3.api.domains.Products;

import java.math.BigInteger;

import com.time3.api.shared.GenericSchema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Product extends GenericSchema {

    @Column(unique = true)
    private String name;

    @Column
    private String description;

    @Column
    private BigInteger price;

    @Column
    private BigInteger stock;
}
