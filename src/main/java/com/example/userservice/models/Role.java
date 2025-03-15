package com.example.userservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role extends BaseModel{
    private String name;

    public String getRole() {
        return name;
    }

    public void setRole(String name) {
        this.name = name;
    }
}
