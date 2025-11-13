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
public class TradeHistoryDTO {
    private Long id;
    private String operationNumber;
    private LocalDateTime operationTime;
    private String operationName;
    private Integer contractQuantity;
    private BigDecimal stockPrice;
    private String operationType;
    private String symbol;
    private BigDecimal totalAmount;
    private String status;
    private String userUUID;  //  String (UUID del usuario)
    private LocalDateTime createdAt;
}
