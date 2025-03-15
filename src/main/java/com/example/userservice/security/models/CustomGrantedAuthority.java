package com.example.userservice.security.models;

import com.example.userservice.models.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class CustomGrantedAuthority implements GrantedAuthority {

    private Role role;

    public CustomGrantedAuthority(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getRole();
    }
}
