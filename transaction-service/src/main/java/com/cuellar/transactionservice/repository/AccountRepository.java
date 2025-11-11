package com.cuellar.transactionservice.repository;

import com.cuellar.transactionservice.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUuid(String uuid);
    Optional<Account> findByCuentaClave(String cuentaClave);
    boolean existsByCuentaClave(String cuentaClave);
}
