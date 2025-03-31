package com.example.crud_api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignore null values in JSON output
public class ErrorResponse {
    private final int status;
    private final String error;
    private final String message; // Changed Object -> String for clarity
    private final String path;

    // Overloaded constructor for simpler errors (e.g., internal server errors)
    public ErrorResponse(int status, String error) {
        this(status, error, null, null);
    }
}
