package com.cuellar.transactionservice.dto;

import com.cuellar.transactionservice.domain.TipoServicio;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PagoServicioRequest {

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    @DecimalMin(value = "0.01", message = "El monto mínimo es 0.01")
    private BigDecimal monto;

    @NotNull(message = "El tipo de servicio es obligatorio")
    private TipoServicio tipoServicio;

    @NotBlank(message = "La referencia del servicio es obligatoria")
    @Size(max = 100, message = "La referencia no puede exceder 100 caracteres")
    private String referenciaServicio;

    // Getters y Setters
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public TipoServicio getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(TipoServicio tipoServicio) { this.tipoServicio = tipoServicio; }

    public String getReferenciaServicio() { return referenciaServicio; }
    public void setReferenciaServicio(String referenciaServicio) { this.referenciaServicio = referenciaServicio; }
}
