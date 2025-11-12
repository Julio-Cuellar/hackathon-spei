package com.cuellar.financialstruments.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreditoResponse {
    private Long ID;
    private String userUUID;
    private String banco;
    private BigDecimal monto;
    private LocalDate fechaCorte;
    private LocalDate fechaPago;
    private BigDecimal tasaInteres;
    private String status;
    private LocalDateTime fechaCreacion;

    public Long getID() { return ID; }
    public void setID(Long ID) { this.ID = ID; }
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
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
