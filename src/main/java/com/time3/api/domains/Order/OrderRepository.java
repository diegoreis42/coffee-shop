package com.time3.api.domains.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.time3.api.domains.User.User;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Page<Order> findAllByUser(PageRequest page, User userRef);

    Optional<Order> findByIdAndUser(UUID id, User userRef);
}
