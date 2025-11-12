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
public class CreateUserRequestDTO {

    private String userUUID;  // ← UUID recibido del cliente (OBLIGATORIO)
    private BigDecimal balance;  // Opcional, default: 10000.00
}
