package com.brokerservice.brocker_service.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeRequestDTO {
    private String userId;  //  String (UUID del usuario)
    private String symbol;
    private Integer quantity;
    private BigDecimal price;
    private String operationType;
}