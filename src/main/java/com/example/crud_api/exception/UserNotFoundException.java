package com.example.crud_api.exception;
import com.example.crud_api.exception.UserNotFoundException;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
