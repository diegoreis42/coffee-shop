package com.time3.api.domains.Products;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findByName(String name);

    Page<Product> findAll(@NonNull Pageable pageable);
}
