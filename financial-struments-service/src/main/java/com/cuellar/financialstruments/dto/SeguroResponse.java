package com.cuellar.financialstruments.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SeguroResponse {
    private Long ID;
    private String userUUID;
    private String tipoSeguro;
    private String aseguradora;
    private String numeroPoliza;
    private BigDecimal sumaAsegurada;
    private BigDecimal primaMensual;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private Boolean activo;
    private LocalDateTime fechaCreacion;

    public Long getID() { return ID; }
    public void setID(Long ID) { this.ID = ID; }
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
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
