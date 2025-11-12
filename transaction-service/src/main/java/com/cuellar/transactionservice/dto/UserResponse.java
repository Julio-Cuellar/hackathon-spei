package com.cuellar.transactionservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserResponse {
    private Long id;
    private String uuid;
    private String cuentaClave;
    private BigDecimal saldo;
    private LocalDateTime fechaCreacion;
    private Boolean activa;

    public UserResponse() {}

    // ==================== GETTERS Y SETTERS ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getCuentaClave() { return cuentaClave; }
    public void setCuentaClave(String cuentaClave) { this.cuentaClave = cuentaClave; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
}
