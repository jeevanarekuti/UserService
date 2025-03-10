package com.example.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.userservice.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User save(User user);

}
