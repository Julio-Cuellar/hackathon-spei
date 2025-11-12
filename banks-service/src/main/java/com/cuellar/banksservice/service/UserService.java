package com.cuellar.banksservice.service;

import com.cuellar.banksservice.domain.User;
import com.cuellar.banksservice.dto.UpdateCuentaClabeRequest;
import com.cuellar.banksservice.dto.UserDTO;
import com.cuellar.banksservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BankEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public Optional<User> findByUUID(String userUUID) {
        return userRepository.findByUUID(userUUID);
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

    // NUEVO MÉTODO: Agregar cuenta CLABE y publicar evento
    @Transactional
    public UserDTO addCuentaClabe(String UUID, UpdateCuentaClabeRequest request) {
        User user = userRepository.findByUUID(UUID)
                .orElseThrow(() -> new RuntimeException("User not found with UUID: " + UUID));

        // Verificar si la cuenta CLABE cambió
        boolean cuentaClabeChanged = !request.getCuentaClabe().equals(user.getCuentaClabe());

        // Actualizar campos bancarios
        user.setCuentaClabe(request.getCuentaClabe());
        user.setBankName(request.getBankName());
        user.setNumeroCuenta(request.getNumeroCuenta());

        User updatedUser = userRepository.save(user);
        log.info("Cuenta CLABE agregada para usuario UUID={}: {}", UUID, request.getCuentaClabe());

        // Publicar evento solo si cambió la cuenta CLABE
        if (cuentaClabeChanged) {
            eventPublisher.publishCuentaClabeAdded(
                    updatedUser.getUUID(),
                    updatedUser.getCuentaClabe(),
                    updatedUser.getBankName(),
                    updatedUser.getNumeroCuenta()
            );
        }

        return convertToDTO(updatedUser);
    }

    @Transactional
    public UserDTO updateUser(String UUID, UserDTO userDTO) {
        User user = userRepository.findByUUID(UUID)
                .orElseThrow(() -> new RuntimeException("User not found with UUID: " + UUID));

        // Verificar si se está agregando una cuenta CLABE nueva
        boolean cuentaClabeChanged = false;
        if (userDTO.getCuentaClabe() != null &&
                !userDTO.getCuentaClabe().equals(user.getCuentaClabe())) {
            cuentaClabeChanged = true;
        }

        // Actualizar campos
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }
        if (userDTO.getBankName() != null) {
            user.setBankName(userDTO.getBankName());
        }
        if (userDTO.getNumeroCuenta() != null) {
            user.setNumeroCuenta(userDTO.getNumeroCuenta());
        }
        if (userDTO.getCuentaClabe() != null) {
            user.setCuentaClabe(userDTO.getCuentaClabe());
        }

        User updatedUser = userRepository.save(user);
        log.info("Usuario actualizado: UUID={}", UUID);

        // Publicar evento si se agregó una cuenta CLABE
        if (cuentaClabeChanged && updatedUser.getCuentaClabe() != null) {
            eventPublisher.publishCuentaClabeAdded(
                    updatedUser.getUUID(),
                    updatedUser.getCuentaClabe(),
                    updatedUser.getBankName(),
                    updatedUser.getNumeroCuenta()
            );
        }

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
