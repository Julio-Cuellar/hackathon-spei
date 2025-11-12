package com.brokerservice.brocker_service.repository;

import com.brokerservice.brocker_service.domain.TradeOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeOperationRepository extends JpaRepository<TradeOperation, Long> {

    List<TradeOperation> findByUserIdOrderByOperationTimeDesc(Long userId);

    List<TradeOperation> findByUserIdAndSymbol(Long userId, String symbol);

    @Query("SELECT t FROM TradeOperation t WHERE t.user.id = :userId AND t.symbol = :symbol ORDER BY t.operationTime DESC")
    List<TradeOperation> findUserOperationsBySymbol(@Param("userId") Long userId, @Param("symbol") String symbol);

    long countByUserId(Long userId);
}
