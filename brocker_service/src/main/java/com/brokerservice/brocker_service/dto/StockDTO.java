package com.brokerservice.brocker_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class StockDTO {
    private String symbol;
    private String name;
    private BigDecimal currentPrice;
    private String currency;
    private int quantity; // ✅ AGREGAR ESTE CAMPO
}
