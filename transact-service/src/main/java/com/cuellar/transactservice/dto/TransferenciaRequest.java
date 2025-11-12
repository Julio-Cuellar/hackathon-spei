package com.cuellar.transactservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferenciaRequest {

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    @DecimalMin(value = "0.01", message = "El monto mínimo es 0.01")
    private BigDecimal monto;

    @NotBlank(message = "La cuenta origen es obligatoria")
    @Size(min = 10, max = 50, message = "La cuenta origen debe tener entre 10 y 50 caracteres")
    private String cuentaOrigen;

    @NotBlank(message = "La cuenta clave es obligatoria")
    @Size(min = 10, max = 50, message = "La cuenta clave debe tener entre 10 y 50 caracteres")
    private String cuentaClave;

    @NotBlank(message = "La cuenta destino es obligatoria")
    @Size(min = 10, max = 50, message = "La cuenta destino debe tener entre 10 y 50 caracteres")
    private String cuentaDestino;

    @NotBlank(message = "El concepto es obligatorio")
    @Size(max = 200, message = "El concepto no puede exceder 200 caracteres")
    private String concepto;
}
