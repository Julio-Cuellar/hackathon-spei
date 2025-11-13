package com.cuellar.financialstruments.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "buro_credito")
public class BuroCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cuenta_uuid", nullable = false, unique = true, length = 36)
    private String cuentaUuid;

    public BuroCredito() {}
    public BuroCredito(String cuentaUuid) {
        this.cuentaUuid = cuentaUuid;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCuentaUuid() { return cuentaUuid; }
    public void setCuentaUuid(String cuentaUuid) { this.cuentaUuid = cuentaUuid; }
}
