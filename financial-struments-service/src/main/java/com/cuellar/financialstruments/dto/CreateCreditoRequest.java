package com.cuellar.financialstruments.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateCreditoRequest {
    @NotBlank
    private String userUUID;
    @NotBlank
    private String banco;
    @NotNull
    private BigDecimal monto;
    @NotNull
    private LocalDate fechaCorte;
    @NotNull
    private LocalDate fechaPago;
    @NotNull
    private BigDecimal tasaInteres;

    public String getUserUUID() { return userUUID; }
    public void setUserUUID(String userUUID) { this.userUUID = userUUID; }
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public LocalDate getFechaCorte() { return fechaCorte; }
    public void setFechaCorte(LocalDate fechaCorte) { this.fechaCorte = fechaCorte; }
    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }
    public BigDecimal getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(BigDecimal tasaInteres) { this.tasaInteres = tasaInteres; }
}
