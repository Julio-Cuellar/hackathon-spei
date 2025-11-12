package com.cuellar.financialstruments.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "buro_credito")
public class BuroCredito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long ID;

    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String userUUID;

    @Column(name = "rfc", nullable = false, length = 13)
    private String rfc;

    public BuroCredito() {}

    public BuroCredito(String userUUID, String rfc) {
        this.userUUID = userUUID;
        this.rfc = rfc;
    }

    public Long getID() { return ID; }
    public void setID(Long ID) { this.ID = ID; }

    public String getUserUUID() { return userUUID; }
    public void setUserUUID(String userUUID) { this.userUUID = userUUID; }

    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }
}
