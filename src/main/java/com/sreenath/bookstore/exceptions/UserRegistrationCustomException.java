package com.sreenath.bookstore.exceptions;

public class UserRegistrationCustomException extends RuntimeException{
    public UserRegistrationCustomException(String message) {
        super(message);
    }
}
