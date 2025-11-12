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

    @NotNull(message = "UserId es requerido")
    private String userId;

    @NotBlank(message = "Symbol es requerido")
    @Pattern(regexp = "^[A-Z]{1,5}$", message = "Symbol debe tener entre 1-5 letras mayúsculas")
    private String symbol;

    @NotNull(message = "Quantity es requerida")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;

    @NotNull(message = "Price es requerido")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal price;

    @NotBlank(message = "OperationType es requerido")
    @Pattern(regexp = "BUY|SELL", message = "OperationType debe ser BUY o SELL")
    private String operationType;
}