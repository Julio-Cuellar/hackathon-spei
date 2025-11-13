package com.cuellar.financialstruments.dto;

public class BuroCreditoResponse {
    private String cuentaUuid;
    public BuroCreditoResponse() {}
    public BuroCreditoResponse(String cuentaUuid) { this.cuentaUuid = cuentaUuid; }
    public String getCuentaUuid() { return cuentaUuid; }
    public void setCuentaUuid(String cuentaUuid) { this.cuentaUuid = cuentaUuid; }
}
