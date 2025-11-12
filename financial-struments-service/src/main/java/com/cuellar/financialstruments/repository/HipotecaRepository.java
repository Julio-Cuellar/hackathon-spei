package com.cuellar.financialstruments.repository;

import com.cuellar.financialstruments.domain.Hipoteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HipotecaRepository extends JpaRepository<Hipoteca, Long> {
    Optional<Hipoteca> findByUuid(String uuid);
    List<Hipoteca> findByCuentaUuid(String cuentaUuid);
}
