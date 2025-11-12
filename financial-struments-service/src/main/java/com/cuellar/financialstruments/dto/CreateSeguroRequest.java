package com.cuellar.financialstruments.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateSeguroRequest {
    @NotBlank
    private String userUUID;
    @NotBlank
    private String tipoSeguro;
    @NotBlank
    private String aseguradora;
    @NotBlank
    private String numeroPoliza;
    @NotNull
    private BigDecimal sumaAsegurada;
    @NotNull
    private BigDecimal primaMensual;
    @NotNull
    private LocalDate fechaInicio;
    @NotNull
    private LocalDate fechaVencimiento;

    public String getUserUUID() { return userUUID; }
    public void setUserUUID(String userUUID) { this.userUUID = userUUID; }
    public String getTipoSeguro() { return tipoSeguro; }
    public void setTipoSeguro(String tipoSeguro) { this.tipoSeguro = tipoSeguro; }
    public String getAseguradora() { return aseguradora; }
    public void setAseguradora(String aseguradora) { this.aseguradora = aseguradora; }
    public String getNumeroPoliza() { return numeroPoliza; }
    public void setNumeroPoliza(String numeroPoliza) { this.numeroPoliza = numeroPoliza; }
    public BigDecimal getSumaAsegurada() { return sumaAsegurada; }
    public void setSumaAsegurada(BigDecimal sumaAsegurada) { this.sumaAsegurada = sumaAsegurada; }
    public BigDecimal getPrimaMensual() { return primaMensual; }
    public void setPrimaMensual(BigDecimal primaMensual) { this.primaMensual = primaMensual; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}
