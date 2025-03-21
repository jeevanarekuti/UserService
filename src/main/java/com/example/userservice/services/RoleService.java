package com.example.userservice.services;

import com.example.userservice.models.Role;
import com.example.userservice.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String name) {
        Role role = new Role();
        role.setRole(name);
        roleRepository.save(role);
        return role;
    }
}
