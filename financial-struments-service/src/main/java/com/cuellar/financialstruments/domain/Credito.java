package com.cuellar.financialstruments.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "creditos")
public class Credito {
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

    @Column(name = "fecha_corte", nullable = false)
    private LocalDate fechaCorte;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "tasa_interes", nullable = false, precision = 5, scale = 2)
    private BigDecimal tasaInteres;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CreditoStatus status = CreditoStatus.ACTIVO;

    // Constructor vacío (OBLIGATORIO para JPA)
    public Credito() {}

    // Constructor con parámetros (usado en el Service)
    public Credito(String userUUID, String banco, BigDecimal monto, LocalDate fechaCorte, LocalDate fechaPago, BigDecimal tasaInteres) {
        this.userUUID = userUUID;
        this.banco = banco;
        this.monto = monto;
        this.fechaCorte = fechaCorte;
        this.fechaPago = fechaPago;
        this.tasaInteres = tasaInteres;
        this.fechaCreacion = LocalDateTime.now();
        this.status = CreditoStatus.ACTIVO;
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

    public LocalDate getFechaCorte() { return fechaCorte; }
    public void setFechaCorte(LocalDate fechaCorte) { this.fechaCorte = fechaCorte; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }

    public BigDecimal getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(BigDecimal tasaInteres) { this.tasaInteres = tasaInteres; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public CreditoStatus getStatus() { return status; }
    public void setStatus(CreditoStatus status) { this.status = status; }
}
