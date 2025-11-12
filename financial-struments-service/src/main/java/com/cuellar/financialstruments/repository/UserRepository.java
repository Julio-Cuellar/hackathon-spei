package com.cuellar.financialstruments.repository;

import com.cuellar.financialstruments.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserUUID(String userUUID);
}
