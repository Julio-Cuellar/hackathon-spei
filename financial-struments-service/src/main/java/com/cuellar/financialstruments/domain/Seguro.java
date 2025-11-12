package com.cuellar.financialstruments.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguros")
public class Seguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long ID;

    @Column(name = "UUID", nullable = false, length = 36)
    private String userUUID;

    @Column(name = "tipo_seguro", nullable = false, length = 100)
    private String tipoSeguro;

    @Column(name = "aseguradora", nullable = false, length = 100)
    private String aseguradora;

    @Column(name = "numero_poliza", nullable = false, unique = true, length = 50)
    private String numeroPoliza;

    @Column(name = "suma_asegurada", nullable = false, precision = 19, scale = 2)
    private BigDecimal sumaAsegurada;

    @Column(name = "prima_mensual", nullable = false, precision = 19, scale = 2)
    private BigDecimal primaMensual;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    public Seguro() {}

    public Seguro(String userUUID, String tipoSeguro, String aseguradora, String numeroPoliza,
                  BigDecimal sumaAsegurada, BigDecimal primaMensual, LocalDate fechaInicio,
                  LocalDate fechaVencimiento) {
        this.userUUID = userUUID;
        this.tipoSeguro = tipoSeguro;
        this.aseguradora = aseguradora;
        this.numeroPoliza = numeroPoliza;
        this.sumaAsegurada = sumaAsegurada;
        this.primaMensual = primaMensual;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
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

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
