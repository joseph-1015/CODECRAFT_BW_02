package com.example.crud_api.service;

import com.example.crud_api.exception.UserNotFoundException;
import com.example.crud_api.model.User;
import com.example.crud_api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE - Add a new user
    public User addUser(User user) {
        logger.info("Received request to create user: {}", user);
    
        boolean emailExists = userRepository.existsByEmail(user.getEmail());
        logger.info("Checking if email exists: {}", emailExists);
        
        if (emailExists) {
            logger.warn("Attempt to add user with existing email: {}", user.getEmail());
            throw new IllegalArgumentException("Email already exists");
        }
    
        User savedUser = userRepository.save(user);
        logger.info("User created: {}", savedUser);
        return savedUser;
    }
    
    
    // READ - Get all users
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        logger.info("Retrieved {} users from DB", users.size());
        return users;
    }

    // READ - Get user by ID
    public User getUserById(UUID id) {
        return userRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("User with ID {} not found", id);
                return new UserNotFoundException("User with ID " + id + " not found");
            });
    }

    // UPDATE - Update user by ID
    public User updateUser(UUID id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            if (!existingUser.getEmail().equals(updatedUser.getEmail()) &&
                userRepository.existsByEmail(updatedUser.getEmail())) {
                logger.warn("Attempt to update user with existing email: {}", updatedUser.getEmail());
                throw new IllegalArgumentException("Email already in use");
            }
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAge(updatedUser.getAge());

            User savedUser = userRepository.save(existingUser);
            logger.info("User updated: {}", savedUser);
            return savedUser;
        }).orElseThrow(() -> {
            logger.warn("User with ID {} not found for update", id);
            return new UserNotFoundException("User with ID " + id + " not found");
        });
    }

    // DELETE - Remove user by ID
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            logger.warn("Attempt to delete non-existing user with ID {}", id);
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
        logger.info("User with ID {} deleted", id);
    }
}
