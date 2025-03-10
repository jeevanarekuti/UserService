package com.example.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.userservice.models.Role;

import java.util.List;


public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByIdIn(List<Long> roleIds);
    Role save(Role role);
}
