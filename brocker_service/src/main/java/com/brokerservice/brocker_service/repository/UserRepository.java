package com.brokerservice.brocker_service.repository;

import com.brokerservice.brocker_service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUuid(String uuid); // ✅ Nuevo método para buscar por UUID

    boolean existsByUuid(String uuid); // ✅ Para validar si existe el UUID
}