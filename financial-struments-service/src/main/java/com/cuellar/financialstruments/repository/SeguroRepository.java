package com.cuellar.financialstruments.repository;

import com.cuellar.financialstruments.domain.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguroRepository extends JpaRepository<Seguro, Long> {
    List<Seguro> findByUserUUID(String userUUID);
}
