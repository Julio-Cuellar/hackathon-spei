package com.cuellar.financialstruments.repository;

import com.cuellar.financialstruments.domain.BuroCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuroCreditoRepository extends JpaRepository<BuroCredito, Long> {
    Optional<BuroCredito> findByCuentaUuid(String cuentaUuid);
}
