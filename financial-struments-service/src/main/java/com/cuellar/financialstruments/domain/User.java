package com.cuellar.financialstruments.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long ID;

    @Column(name = "UUID", nullable = false, unique = true, length = 36)
    private String userUUID;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    public User() {}

    public User(String userUUID) {
        this.userUUID = userUUID;
        this.fechaCreacion = LocalDateTime.now();
    }

    @PrePersist
    public void generateUUID() {
        if (this.userUUID == null) {
            this.userUUID = UUID.randomUUID().toString();
        }
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
    }

    // Getters y setters
    public Long getID() { return ID; }
    public void setID(Long ID) { this.ID = ID; }

    public String getUserUUID() { return userUUID; }
    public void setUserUUID(String userUUID) { this.userUUID = userUUID; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
