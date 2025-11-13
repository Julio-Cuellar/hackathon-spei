package com.cuellar.financialstruments.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_users")
public class FinancialUser {

    @Id
    @Column(name = "uuid", length = 36)
    private String uuid;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    public FinancialUser() {}

    public FinancialUser(String uuid) {
        this.uuid = uuid;
        this.registeredAt = LocalDateTime.now();
    }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }}