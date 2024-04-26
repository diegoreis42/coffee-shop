package com.time3.api.domains.Products;

import java.math.BigInteger;
import java.util.Objects;

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

    @Column
    private SizeEnum size;

    @Column
    private Double rating = 0.0;

    @Column
    private BigInteger ratingCount = BigInteger.ZERO;

    @Column(name = "url_image")
    private String urlImage;

    public Product(String name, String description, BigInteger price, BigInteger stock, String urlImage) {
        super();
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.urlImage = urlImage;
    }

    public void setRating(Double newRating) {
        if (Objects.isNull(this.rating) || this.ratingCount.equals(BigInteger.ZERO)) {
            this.rating = newRating;
            this.ratingCount = BigInteger.ONE;
        } else {
            this.rating = calculateNewAverage(newRating);
            this.ratingCount = this.ratingCount.add(BigInteger.ONE);
        }
    }

    private Double calculateNewAverage(Double newRating) {
        return this.rating * this.ratingCount.doubleValue()
                + newRating / this.ratingCount.add(BigInteger.ONE).doubleValue();
    }
}