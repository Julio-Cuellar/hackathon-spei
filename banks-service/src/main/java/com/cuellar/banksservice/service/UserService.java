package com.cuellar.banksservice.service;

import com.cuellar.banksservice.domain.User;
import com.cuellar.banksservice.dto.CreateUserResponse;
import com.cuellar.banksservice.dto.UserDTO;
import com.cuellar.banksservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse createUser(UserDTO userDTO) {
        // VALIDACIÓN IMPORTANTE: El UUID debe venir del request
        if (userDTO.getUUID() == null || userDTO.getUUID().trim().isEmpty()) {
            throw new IllegalArgumentException("UUID is required and must be provided by the calling service");
        }

        User user = User.builder()
                .UUID(userDTO.getUUID())
                .name(userDTO.getName())
                .lastName(userDTO.getLastName())
                .bankName(userDTO.getBankName())
                .numeroCuenta(userDTO.getNumeroCuenta())
                .cuentaClabe(userDTO.getCuentaClabe())
                .monto("0.00")
                .build();

        User savedUser = userRepository.save(user);

        return CreateUserResponse.builder()
                .UUID(savedUser.getUUID())
                .name(savedUser.getName())
                .lastName(savedUser.getLastName())
                .bankName(savedUser.getBankName())
                .numeroCuenta(savedUser.getNumeroCuenta())
                .cuentaClabe(savedUser.getCuentaClabe())
                .build();
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserByUUID(String UUID) {
        User user = userRepository.findByUUID(UUID)
                .orElseThrow(() -> new RuntimeException("User not found with UUID: " + UUID));
        return convertToDTO(user);
    }

    @Transactional
    public UserDTO updateUser(String UUID, UserDTO userDTO) {
        User user = userRepository.findByUUID(UUID)
                .orElseThrow(() -> new RuntimeException("User not found with UUID: " + UUID));

        if (userDTO.getUUID() != null && !userDTO.getUUID().trim().isEmpty()) {
            user.setUUID(userDTO.getUUID());
        }

        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setBankName(userDTO.getBankName());
        user.setNumeroCuenta(userDTO.getNumeroCuenta());
        user.setCuentaClabe(userDTO.getCuentaClabe());

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(String UUID) {
        User user = userRepository.findByUUID(UUID)
                .orElseThrow(() -> new RuntimeException("User not found with UUID: " + UUID));
        userRepository.delete(user);
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .UUID(user.getUUID())
                .name(user.getName())
                .lastName(user.getLastName())
                .bankName(user.getBankName())
                .numeroCuenta(user.getNumeroCuenta())
                .cuentaClabe(user.getCuentaClabe())
                .build();
    }
}
