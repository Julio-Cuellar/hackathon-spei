package com.cuellar.transactionservice.dto;

import com.cuellar.transactionservice.domain.TipoServicio;
import com.cuellar.transactionservice.domain.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    private Long id;
    private String transactionId;  // ← Debe ser String, NO UUID
    private TransactionType transactionType;
    private BigDecimal monto;
    private LocalDateTime fechaHora;
    private String status;

    private String cuentaOrigen;
    private String cuentaClave;
    private String cuentaDestino;
    private String concepto;

    private TipoServicio tipoServicio;
    private String referenciaServicio;

    public TransactionResponse() {}

    // ==================== GETTERS Y SETTERS ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTransactionId() { return transactionId; }  // ← String
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }  // ← String

    public TransactionType getTransactionType() { return transactionType; }
    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

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
}
