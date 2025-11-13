package com.cuellar.financialstruments.repository;

import com.cuellar.financialstruments.domain.FinancialUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialUserRepository extends JpaRepository<FinancialUser, String> {
    boolean existsByUuid(String uuid);
}