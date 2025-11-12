package com.cuellar.transactionservice.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String userUUID;

    @Column(name = "cuenta_clave", nullable = false, unique = true, length = 18)
    private String cuentaClave;

    @Column(name = "saldo", nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "activa", nullable = false)
    private Boolean activa = true;

    @OneToMany(mappedBy = "cuentaOrigenAccount", fetch = FetchType.LAZY)
    private List<Transaction> transaccionesOrigen = new ArrayList<>();

    @OneToMany(mappedBy = "cuentaDestinoAccount", fetch = FetchType.LAZY)
    private List<Transaction> transaccionesDestino = new ArrayList<>();

    public User() {}

    @PrePersist
    public void generateIdentifiers() {
        if (this.userUUID == null) {
            this.userUUID = UUID.randomUUID().toString();
        }
        if (this.cuentaClave == null) {
            this.cuentaClave = generateCuentaClave();
        }
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
    }

    private String generateCuentaClave() {
        // Genera una CLABE interbancaria aleatoria de 18 dígitos
        long clabe = (long) (Math.random() * 900000000000000000L) + 100000000000000000L;
        return String.valueOf(clabe);
    }

    // ==================== GETTERS Y SETTERS ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserUUID() { return userUUID; }
    public void setUserUUID(String uuid) { this.userUUID = uuid; }

    public String getCuentaClave() { return cuentaClave; }
    public void setCuentaClave(String cuentaClave) { this.cuentaClave = cuentaClave; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }

    public List<Transaction> getTransaccionesOrigen() { return transaccionesOrigen; }
    public void setTransaccionesOrigen(List<Transaction> transaccionesOrigen) { this.transaccionesOrigen = transaccionesOrigen; }

    public List<Transaction> getTransaccionesDestino() { return transaccionesDestino; }
    public void setTransaccionesDestino(List<Transaction> transaccionesDestino) { this.transaccionesDestino = transaccionesDestino; }
}
