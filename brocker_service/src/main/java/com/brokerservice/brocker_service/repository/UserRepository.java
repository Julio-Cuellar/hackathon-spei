package com.brokerservice.brocker_service.repository;

import com.brokerservice.brocker_service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUuid(String uuid);

    boolean existsByUuid(String uuid);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tradeOperations WHERE u.id = :userId")
    Optional<User> findByIdWithOperations(Long userId);
}
