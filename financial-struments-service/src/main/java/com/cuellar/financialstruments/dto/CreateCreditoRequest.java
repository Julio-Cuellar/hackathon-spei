package com.cuellar.financialstruments.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateCreditoRequest {
    private String uuid;  // ✅ UUID del usuario
    private String cuentaUuid;
    private String banco;
    private BigDecimal monto;
    private LocalDate fechaCorte;
    private LocalDate fechaPago;
    private BigDecimal tasaInteres;

    // Getters y Setters
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getCuentaUuid() { return cuentaUuid; }
    public void setCuentaUuid(String cuentaUuid) { this.cuentaUuid = cuentaUuid; }

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