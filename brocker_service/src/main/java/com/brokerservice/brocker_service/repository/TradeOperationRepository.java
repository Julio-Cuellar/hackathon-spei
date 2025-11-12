package com.brokerservice.brocker_service.repository;

import com.brokerservice.brocker_service.domain.TradeOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeOperationRepository extends JpaRepository<TradeOperation, Long> {


    List<TradeOperation> findByUser_UserUUIDOrderByCreatedAtDesc(String userUUID);
    @Query("SELECT t FROM TradeOperation t WHERE t.user.userUUID = :userUUID ORDER BY t.createdAt DESC")
    List<TradeOperation> findByUserUUID(@Param("userUUID") String userUUID);
    long countByUser_UserUUID(String userUUID);
    List<TradeOperation> findByUser_UserUUIDAndSymbol(String userUUID, String symbol);
}
