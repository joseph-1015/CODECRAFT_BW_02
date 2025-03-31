package com.example.crud_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crud_api.model.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}
