package com.cuellar.financialstruments.repository;

import com.cuellar.financialstruments.domain.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeguroRepository extends JpaRepository<Seguro, Long> {
    Optional<Seguro> findByUuid(String uuid);
    List<Seguro> findByCuentaUuid(String cuentaUuid);
}
