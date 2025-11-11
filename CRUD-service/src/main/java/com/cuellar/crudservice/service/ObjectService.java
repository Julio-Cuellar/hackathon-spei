package com.cuellar.crudservice.service;


import com.cuellar.crudservice.domain.ObjectEntity;
import com.cuellar.crudservice.domain.User;
import com.cuellar.crudservice.dto.ObjectDTO;
import com.cuellar.crudservice.repository.ObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ObjectService {

    private final ObjectRepository objectRepository;
    private final UserService userService;

    @Transactional
    public ObjectDTO create(String userUUID, ObjectDTO objectDTO) {
        User user = userService.getOrCreateUser(userUUID); // escritura permitida
        ObjectEntity entity = ObjectEntity.builder()
                .user(user)
                .name(objectDTO.getName())
                .build();
        ObjectEntity saved = objectRepository.save(entity);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<ObjectDTO> findAllByUser(String userUUID) {
        // No crear usuario en lecturas
        return objectRepository.findByUserUserUUID(userUUID)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ObjectDTO findById(Long id, String userUUID) {
        // No crear usuario en lecturas
        return objectRepository.findById(id)
                .filter(obj -> obj.getUser().getUserUUID().equals(userUUID))
                .map(this::toDTO)
                .orElse(null);
    }

    @Transactional
    public ObjectDTO update(Long id, String userUUID, ObjectDTO objectDTO) {
        User user = userService.getOrCreateUser(userUUID); // escritura permitida
        return objectRepository.findById(id)
                .filter(obj -> obj.getUser().getUserUUID().equals(userUUID))
                .map(entity -> {
                    entity.setName(objectDTO.getName());
                    return toDTO(objectRepository.save(entity));
                })
                .orElse(null);
    }

    @Transactional
    public boolean delete(Long id, String userUUID) {
        User user = userService.getOrCreateUser(userUUID); // escritura permitida
        return objectRepository.findById(id)
                .filter(obj -> obj.getUser().getUserUUID().equals(userUUID))
                .map(entity -> {
                    objectRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    private ObjectDTO toDTO(ObjectEntity entity) {
        return ObjectDTO.builder()
                .id(entity.getId())
                .owner_UUID(entity.getUser().getUserUUID())
                .name(entity.getName())
                .build();
    }
}