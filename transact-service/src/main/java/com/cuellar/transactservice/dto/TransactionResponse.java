package com.cuellar.transactservice.dto;

import com.cuellar.transactservice.domain.TipoServicio;
import com.cuellar.transactservice.domain.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private String transactionId;  // ← Debe ser String, NO UUID
    private TransactionType transactionType;
    private BigDecimal monto;
    private LocalDateTime fechaHora;
    private String status;

    private String cuentaOrigen;
    private String cuentaClave;
    private String cuentaDestino;
    private String concepto;

    private TipoServicio tipoServicio;
    private String referenciaServicio;
}