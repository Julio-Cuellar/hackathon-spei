package com.cuellar.transactionservice.repository;

import com.cuellar.transactionservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUuid(String uuid);
    Optional<User> findByCuentaClave(String cuentaClave);
    boolean existsByCuentaClave(String cuentaClave);
}
