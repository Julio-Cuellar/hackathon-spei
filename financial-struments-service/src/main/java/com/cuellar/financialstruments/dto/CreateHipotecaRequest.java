package com.cuellar.financialstruments.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class CreateHipotecaRequest {
    @NotBlank
    private String cuentaUuid;
    @NotBlank
    private String banco;
    @NotNull
    private BigDecimal monto;
    @NotNull
    private Integer plazoMeses;

    public String getCuentaUuid() { return cuentaUuid; }
    public void setCuentaUuid(String cuentaUuid) { this.cuentaUuid = cuentaUuid; }
    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public Integer getPlazoMeses() { return plazoMeses; }
    public void setPlazoMeses(Integer plazoMeses) { this.plazoMeses = plazoMeses; }
}
