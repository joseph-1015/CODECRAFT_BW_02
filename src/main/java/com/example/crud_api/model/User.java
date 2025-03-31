package com.example.crud_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users") // Table name in MySQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Simplified UUID generation
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.BINARY) // Ensures correct UUID to BINARY conversion
    private UUID id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Positive(message = "Age must be a positive number")
@Max(value = 120, message = "Age must be realistic")
@Column(name = "age", nullable = false)
private int age;


    // Constructor without ID (Hibernate will auto-generate it)
    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

     @JsonProperty("password")  // Ensure JSON deserialization works
    private String password;

    private String role = "USER"; // Default role

    // Getters and Setters...
}
