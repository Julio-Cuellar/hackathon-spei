package com.cuellar.financialstruments.repository;

import com.cuellar.financialstruments.domain.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {
    Optional<Credito> findByUuid(String uuid);
    List<Credito> findByCuentaUuid(String cuentaUuid);
}
