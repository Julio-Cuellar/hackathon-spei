package com.cuellar.transactservice.repository;

import com.cuellar.transactservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserUUID(String userUUID);
    Optional<User> findByCuentaClave(String cuentaClave);
    boolean existsByCuentaClave(String cuentaClave);
}
