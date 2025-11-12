package com.cuellar.transactionservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class CreateUserRequest {

    @NotNull(message = "El saldo inicial es obligatorio")
    @DecimalMin(value = "0.00", message = "El saldo inicial no puede ser negativo")
    private BigDecimal saldoInicial;

    public CreateUserRequest() {}

    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }
}
