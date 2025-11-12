package com.cuellar.banksservice.repository;

import com.cuellar.banksservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {  // Cambiar Long a String

    Optional<User> findByUUID(String UUID);
}
