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

    @NotNull(message = "El ID de usuario es requerido")
    @Positive(message = "El ID de usuario debe ser positivo")
    private Long userId;

    @NotBlank(message = "El símbolo de la acción es requerido")
    @Size(min = 1, max = 10, message = "El símbolo debe tener entre 1 y 10 caracteres")
    private String symbol;

    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad mínima es 1")
    @Max(value = 10000, message = "La cantidad máxima es 10,000")
    private Integer quantity;

    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.01", message = "El precio mínimo es 0.01")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 10 enteros y 2 decimales")
    private BigDecimal price;

    @NotBlank(message = "El tipo de operación es requerido")
    @Pattern(regexp = "^(BUY|SELL)$", message = "El tipo de operación debe ser BUY o SELL")
    private String operationType;
}
