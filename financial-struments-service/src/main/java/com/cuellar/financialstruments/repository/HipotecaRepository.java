package com.cuellar.financialstruments.repository;

import com.cuellar.financialstruments.domain.Hipoteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HipotecaRepository extends JpaRepository<Hipoteca, Long> {
    List<Hipoteca> findByUserUUID(String userUUID);
}
