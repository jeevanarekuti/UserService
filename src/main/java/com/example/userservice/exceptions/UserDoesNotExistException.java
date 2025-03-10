package com.example.userservice.exceptions;

public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(String s) {
        super(s);
    }
}
