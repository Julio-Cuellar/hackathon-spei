package com.cuellar.financialstruments.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hipotecas")
public class Hipoteca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long ID;

    @Column(name = "UUID", nullable = false, length = 36)
    private String userUUID;

    @Column(name = "banco", nullable = false, length = 100)
    private String banco;

    @Column(name = "monto", nullable = false, precision = 19, scale = 2)
    private BigDecimal monto;

    @Column(name = "plazo_meses", nullable = false)
    private Integer plazoMeses;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    public Hipoteca() {}

    public Hipoteca(String userUUID, String banco, BigDecimal monto, Integer plazoMeses) {
        this.userUUID = userUUID;
        this.banco = banco;
        this.monto = monto;
        this.plazoMeses = plazoMeses;
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
    }

    @PrePersist
    public void setDefaults() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
    }

    // Getters y setters
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

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
