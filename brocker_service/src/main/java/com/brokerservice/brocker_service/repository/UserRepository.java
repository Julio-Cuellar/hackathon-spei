package com.brokerservice.brocker_service.repository;

import com.brokerservice.brocker_service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {  // ← String, no Long

    // findById(String uuid) ya está disponible automáticamente

    // Métodos adicionales si los necesitas:
    boolean existsByUserUUID(String userUUID);

    // Si tienes username o email:
    // Optional<User> findByEmail(String email);
    // Optional<User> findByUsername(String username);
}
