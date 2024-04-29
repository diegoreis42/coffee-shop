package com.time3.api.domains.Products;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.time3.api.domains.Products.dtos.ProductDto;

@ExtendWith(MockitoExtension.class)
public class ProductUseCasesTest {

    private ProductDto productDto;

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductUseCases productUseCases;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto("Café", "Um ótimo cafézinho", BigInteger.valueOf(19999),
                BigInteger.valueOf(200), "https://www.cafezinho.com.br");
    }

    @Test
    void shouldThrowExceptionWhenProductAlreadyExists() {
        Product existingProduct = new Product("Café", "Um ótimo cafézinho", BigInteger.valueOf(1999),
                BigInteger.valueOf(100), "https://www.cafezinho.com.br");

        when(repository.findByName("Café")).thenReturn(Optional.of(existingProduct));

        assertThrows(ProductException.ProductAlreadyExists.class, () -> {
            productUseCases.create(productDto);
        });

        verify(repository, never()).save(any(Product.class));
    }

    @Test
    void shouldSaveProductWhenNoExistingProductFound() {
        ProductDto productDto = new ProductDto("Cafézinho mineiro", "café top", BigInteger.valueOf(1999),
                BigInteger.valueOf(100), "https://www.cafezinho.com.br");

        when(repository.findByName("Cafézinho mineiro")).thenReturn(Optional.empty());

        productUseCases.create(productDto);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(repository).save(productCaptor.capture());
        Product savedProduct = productCaptor.getValue();

        assertNotNull(savedProduct);
        assertEquals("Cafézinho mineiro", savedProduct.getName());
        assertEquals("café top", savedProduct.getDescription());
        assertEquals(BigInteger.valueOf(1999), savedProduct.getPrice());
        assertEquals(BigInteger.valueOf(100), savedProduct.getStock());
        assertEquals("https://www.cafezinho.com.br", savedProduct.getUrlImage());
    }

}
