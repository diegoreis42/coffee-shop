package com.time3.api.domains.User;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String username);

    User getReferenceByEmail(String userEmail);
}