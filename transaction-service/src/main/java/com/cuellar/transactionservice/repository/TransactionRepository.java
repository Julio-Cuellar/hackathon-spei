package com.cuellar.transactionservice.repository;

import com.cuellar.transactionservice.domain.Transaction;
import com.cuellar.transactionservice.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionId(String transactionId);  // ← String, NO UUID
    List<Transaction> findByTransactionType(TransactionType transactionType);
    List<Transaction> findByFechaHoraBetween(LocalDateTime startDate, LocalDateTime endDate);
}
