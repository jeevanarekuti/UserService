package com.example.userservice.dtos;

import com.example.userservice.models.SessionStatus;

public class ValidatetokenResponseDto {
    private UserDto userDto;
    private SessionStatus sessionStatus;

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
}
