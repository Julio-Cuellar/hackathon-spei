package com.brokerservice.brocker_service.service;

import com.brokerservice.brocker_service.domain.TradeOperation;
import com.brokerservice.brocker_service.domain.User;
import com.brokerservice.brocker_service.repository.TradeOperationRepository;
import com.brokerservice.brocker_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final UserRepository userRepository;
    private final TradeOperationRepository tradeOperationRepository;

    /**
     * Obtener portfolio de un usuario por UUID
     */
    public List<TradeOperation> getUserPortfolio(String userUUID) {  // ← String, no Long
        return tradeOperationRepository.findByUser_UserUUIDOrderByCreatedAtDesc(userUUID);  // ← CAMBIO
    }

    /**
     * Obtener balance total del portfolio
     */
    public BigDecimal getPortfolioValue(String userUUID) {  // ← String, no Long
        User user = userRepository.findById(userUUID)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + userUUID));

        return user.getBalance();
    }

    /**
     * Contar número de operaciones del usuario
     */
    public long getOperationsCount(String userUUID) {  // ← String, no Long
        return tradeOperationRepository.countByUser_UserUUID(userUUID);
    }
}
