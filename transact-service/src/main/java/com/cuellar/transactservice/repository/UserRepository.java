package com.cuellar.transactservice.repository;

import com.cuellar.transactservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserUUID(String userUUID);
}
