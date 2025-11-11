package com.cuellar.authservice.service;

import com.cuellar.authservice.domain.User;
import com.cuellar.authservice.dto.LoginRequest;
import com.cuellar.authservice.dto.LoginResponse;
import com.cuellar.authservice.dto.RegisterRequest;
import com.cuellar.authservice.dto.UserDTO;
import com.cuellar.authservice.repository.UserRepository;
import com.cuellar.authservice.security.TokenBlacklist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final TokenBlacklist tokenBlacklist;
    private final RestClient.Builder builder;



    // ----UTILS-----

    private UserDTO toDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .build();
    }



    // ------ CREATE-----

    public UserDTO register(RegisterRequest request){

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists"); //si el email ya se encuentra registrado entonces
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists"); // si el usuario ya se encuentra registrado entonces
        }

        User user = User.builder()
                .name(request.getName())

                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .build();

        user = userRepository.save(user);
        return toDto(user);

    }

    // ---------- READ ACTIVOS ----------
    public List<UserDTO> getActiveUsers() {
        return userRepository.findAll().stream()
                .filter(User::isEnabled)
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    // ---------- READ ----------
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return toDto(user);
    }


    // ------ UPDATE ------
    public UserDTO updateUser(UUID id, UserDTO dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setEnabled(dto.isEnabled());
        user = userRepository.save(user);
        return toDto(user);
    }



    // ---------- DELETE ----------
    public void deleteUser(UUID id, String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token vacío.");
        }
        // Limpia el token si viene con "Bearer "
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        } else {
            token = token.trim();
        }

        String username = jwtService.extractUsername(token);
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario autenticado no encontrado"));

        if (!currentUser.getId().equals(id)) {
            throw new SecurityException("No tienes permisos para borrar a este usuario");
        }
        userRepository.deleteById(id);
    }


    // ---------- SOFT DELETE / DESACTIVAR ----------
    public void setUserEnabled(UUID id, boolean enabled) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        user.setEnabled(enabled);
        userRepository.save(user);
    }


    // ------- LOGIN ------

    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña incorrectos"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Usuario o contraseña incorrectos");
        }

        user.setEnabled(true);
        user = userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new LoginResponse(token, toDto(user));
    }


    // ------ LOGOUT -----

    public void logut(String token){
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token vacío");
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        } else {
            token = token.trim();
        }

        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        user.setEnabled(false);
        userRepository.save(user);
        tokenBlacklist.blacklistToken(token);
    }

}

