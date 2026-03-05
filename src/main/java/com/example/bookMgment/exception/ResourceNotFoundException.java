package com.example.bookMgment.exception;

/**
 * Thrown when a requested resource is not found in the database
 * HTTP Status: 404 Not Found
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor with custom message
     */
    public ResourceNotFoundException(String message) {
        super(message);  // This calls the parent RuntimeException constructor
    }

    /**
     * Constructor with message and cause
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);  // Calls RuntimeException constructor with cause
    }

    /**
     * Constructor with resource name and field details
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}