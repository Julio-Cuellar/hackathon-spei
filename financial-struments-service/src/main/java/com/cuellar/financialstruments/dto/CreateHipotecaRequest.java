package com.cuellar.financialstruments.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateHipotecaRequest {
    private String uuid;
    private String cuentaUuid;
    private String banco;
    private BigDecimal monto;
    private Integer plazoMeses;
}