package com.cuellar.transactionservice.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", nullable = false, unique = true, length = 36)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "monto", nullable = false, precision = 19, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    // Campos para TRANSFERENCIA
    @Column(name = "cuenta_origen", length = 50)
    private String cuentaOrigen;

    @Column(name = "cuenta_clave", length = 50)
    private String cuentaClave;

    @Column(name = "cuenta_destino", length = 50)
    private String cuentaDestino;

    @Column(name = "concepto", length = 200)
    private String concepto;

    // Campos para PAGO DE SERVICIOS
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_servicio")
    private TipoServicio tipoServicio;

    @Column(name = "referencia_servicio", length = 100)
    private String referenciaServicio;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "COMPLETADA";

    // RELACIONES CON ACCOUNT
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_origen_id")
    private Account cuentaOrigenAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_destino_id")
    private Account cuentaDestinoAccount;

    public Transaction() {}

    @PrePersist
    public void generateUUID() {
        if (this.transactionId == null) {
            this.transactionId = UUID.randomUUID().toString();
        }
        if (this.fechaHora == null) {
            this.fechaHora = LocalDateTime.now();
        }
    }

    // ==================== GETTERS Y SETTERS ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public TransactionType getTransactionType() { return transactionType; }
    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getCuentaOrigen() { return cuentaOrigen; }
    public void setCuentaOrigen(String cuentaOrigen) { this.cuentaOrigen = cuentaOrigen; }

    public String getCuentaClave() { return cuentaClave; }
    public void setCuentaClave(String cuentaClave) { this.cuentaClave = cuentaClave; }

    public String getCuentaDestino() { return cuentaDestino; }
    public void setCuentaDestino(String cuentaDestino) { this.cuentaDestino = cuentaDestino; }

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }

    public TipoServicio getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(TipoServicio tipoServicio) { this.tipoServicio = tipoServicio; }

    public String getReferenciaServicio() { return referenciaServicio; }
    public void setReferenciaServicio(String referenciaServicio) { this.referenciaServicio = referenciaServicio; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Account getCuentaOrigenAccount() { return cuentaOrigenAccount; }
    public void setCuentaOrigenAccount(Account cuentaOrigenAccount) { this.cuentaOrigenAccount = cuentaOrigenAccount; }

    public Account getCuentaDestinoAccount() { return cuentaDestinoAccount; }
    public void setCuentaDestinoAccount(Account cuentaDestinoAccount) { this.cuentaDestinoAccount = cuentaDestinoAccount; }
}
