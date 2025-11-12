package com.brokerservice.brocker_service.repository;

import com.brokerservice.brocker_service.domain.TradeOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeOperationRepository extends JpaRepository<TradeOperation, Long> {

    // ✅ Usar findByUser_Id (con guión bajo)
    List<TradeOperation> findByUser_Id(Long userId);

    List<TradeOperation> findByUser_IdOrderByOperationTimeDesc(Long userId);

    List<TradeOperation> findBySymbol(String symbol);

    List<TradeOperation> findByOperationType(String operationType);
}