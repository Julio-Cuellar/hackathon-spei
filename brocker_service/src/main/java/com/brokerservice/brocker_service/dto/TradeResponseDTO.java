package com.brokerservice.brocker_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeResponseDTO {
    private Long id;
    private String operationNumber;
    private String operationName;
    private String operationType;
    private String symbol;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime operationTime;
    private String userUUID;  //  String (UUID del usuario)
    private BigDecimal remainingBalance;
}
