package com.cuellar.authservice.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Builder
public class UserDTO {



    private UUID id;

    private String name;

    private String username;

    private String email;

    private String passwordHash;

    private String RFC;

    private String CURP;

    private String PhoneNumber;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private boolean enabled;
}
