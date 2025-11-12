package com.cuellar.financialstruments.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HipotecaResponse {
    private Long ID;
    private String userUUID;
    private String banco;
    private BigDecimal monto;
    private Integer plazoMeses;
    private Boolean activo;
    private LocalDateTime fechaCreacion;

    public Long getID() { return ID; }
    public void setID(Long ID) { this.ID = ID; }
    public String getUserUUID() { return userUUID; }
    public void setUserUUID(String userUUID) { this.userUUID = userUUID; }
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public Integer getPlazoMeses() { return plazoMeses; }
    public void setPlazoMeses(Integer plazoMeses) { this.plazoMeses = plazoMeses; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
