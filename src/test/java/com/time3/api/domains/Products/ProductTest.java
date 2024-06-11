package com.time3.api.domains.Products;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Test Product", "Description", BigInteger.valueOf(1000), BigInteger.valueOf(50),
                "url/to/image");
    }

    @Test
    void testInitialRatingSetCorrectly() {
        product.setRating(4.0);
        assertEquals(4.0, product.getRating(), 0.001, "The rating should be set to 4.0");
        assertEquals(BigInteger.ONE, product.getRatingCount(), "The rating count should be 1");
    }

    @Test
    void testSubsequentRatingUpdatesAverageCorrectly() {
        product.setRating(4.0);

        product.setRating(2.0);

        double expectedRating = (4.0 + 2.0) / 2;
        assertEquals(expectedRating, product.getRating(), 0.001, "The average rating should now be 3.0");
        assertEquals(BigInteger.valueOf(2), product.getRatingCount(), "The rating count should be 2");
    }

    @Test
    void testRatingCannotExceedMaximum() {
        product.setRating(5.0);

        assertEquals(5.0, product.getRating(), 0.001, "The rating should not exceed 5.0");
        assertEquals(BigInteger.valueOf(1), product.getRatingCount(), "The rating count should be 2");
    }

}
