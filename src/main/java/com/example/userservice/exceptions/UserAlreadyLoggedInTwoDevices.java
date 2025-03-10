package com.example.userservice.exceptions;

public class UserAlreadyLoggedInTwoDevices extends Throwable {
    public UserAlreadyLoggedInTwoDevices(String s) {
        super(s);
    }
}
