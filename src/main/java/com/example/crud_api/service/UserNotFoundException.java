package com.example.crud_api.service; // Ensure it's in the correct package

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}




