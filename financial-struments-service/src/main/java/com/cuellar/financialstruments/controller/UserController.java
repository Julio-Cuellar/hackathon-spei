package com.cuellar.financialstruments.controller;

import com.cuellar.financialstruments.domain.User;
import com.cuellar.financialstruments.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User saved = userRepository.save(user);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/uuid/{userUUID}")
    public ResponseEntity<User> getByUserUUID(@PathVariable String userUUID) {
        User user = userRepository.findByUserUUID(userUUID).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
