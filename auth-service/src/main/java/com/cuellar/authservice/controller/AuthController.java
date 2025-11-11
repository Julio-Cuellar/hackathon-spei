package com.cuellar.authservice.controller;

import com.cuellar.authservice.dto.LoginRequest;
import com.cuellar.authservice.dto.LoginResponse;
import com.cuellar.authservice.dto.RegisterRequest;
import com.cuellar.authservice.dto.UserDTO;
import com.cuellar.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService auth;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auth.register(req));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = auth.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader){
        auth.logut(authHeader);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id){
        UserDTO userDTO = auth.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = auth.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @Valid @RequestBody UserDTO userDTO){
        UserDTO updateuser = auth.updateUser(id, userDTO);
        return ResponseEntity.ok(updateuser);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id, @RequestHeader("Authorization")  String authHeader){
        auth.deleteUser(id, authHeader);
        return ResponseEntity.noContent().build();
    }


}
