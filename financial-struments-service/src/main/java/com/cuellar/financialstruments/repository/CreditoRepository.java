package com.cuellar.financialstruments.repository;

import com.cuellar.financialstruments.domain.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {
    List<Credito> findByUserUUID(String userUUID);
}
