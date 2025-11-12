package com.cuellar.authservice.dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class RegisterRequest {


    public String name;

    public String username;

    private String email;

    private String password;


    private String RFC;

    private String CURP;

    private String PhoneNumber;


    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;




}
