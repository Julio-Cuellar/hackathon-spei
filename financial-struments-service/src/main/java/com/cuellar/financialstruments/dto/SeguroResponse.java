package com.cuellar.financialstruments.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SeguroResponse {
    private String uuid;
    private String cuentaUuid;
    private String tipoSeguro;
    private String aseguradora;
    private String numeroPoliza;
    private BigDecimal sumaAsegurada;
    private BigDecimal primaMensual;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private Boolean activo;
    private LocalDateTime fechaCreacion;

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }
    public String getCuentaUuid() { return cuentaUuid; }
    public void setCuentaUuid(String cuentaUuid) { this.cuentaUuid = cuentaUuid; }
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
