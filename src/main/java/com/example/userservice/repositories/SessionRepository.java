package com.example.userservice.repositories;

import com.example.userservice.models.Session;
import com.example.userservice.models.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByTokenAndUser_Id(String token, Long userId);

    long countByUser_IdAndStatus(long userId, SessionStatus status);
}
