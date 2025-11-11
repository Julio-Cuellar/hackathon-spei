package com.cuellar.crudservice.controller;

import com.cuellar.crudservice.dto.ObjectDTO;
import com.cuellar.crudservice.service.ObjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objects")
@RequiredArgsConstructor
@Slf4j
public class ObjectController {

    private final ObjectService objectService;

    private boolean invalidUser(String userUUID) {
        return userUUID == null || userUUID.isBlank();
    }

    private boolean invalidBody(ObjectDTO dto) {
        return dto == null || dto.getName() == null || dto.getName().isBlank();
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID,
            @RequestBody ObjectDTO objectDTO) {

        if (invalidUser(userUUID)) {
            log.warn("Missing or blank X-User-UUID in create");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing X-User-UUID header");
        }
        if (invalidBody(objectDTO)) {
            log.warn("Invalid body in create: missing or blank 'name'");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or blank 'name'");
        }

        ObjectDTO created = objectService.create(userUUID, objectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<?> getAllForUser(
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID) {

        if (invalidUser(userUUID)) {
            log.warn("Missing or blank X-User-UUID in getAllForUser");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing X-User-UUID header");
        }

        List<ObjectDTO> objects = objectService.findAllByUser(userUUID);
        return ResponseEntity.ok(objects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID) {

        if (invalidUser(userUUID)) {
            log.warn("Missing or blank X-User-UUID in getById");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing X-User-UUID header");
        }

        ObjectDTO object = objectService.findById(id, userUUID);
        if (object == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(object);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID,
            @RequestBody ObjectDTO objectDTO) {

        if (invalidUser(userUUID)) {
            log.warn("Missing or blank X-User-UUID in update");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing X-User-UUID header");
        }
        if (invalidBody(objectDTO)) {
            log.warn("Invalid body in update: missing or blank 'name'");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or blank 'name'");
        }

        ObjectDTO updated = objectService.update(id, userUUID, objectDTO);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-UUID", required = false) String userUUID) {

        if (invalidUser(userUUID)) {
            log.warn("Missing or blank X-User-UUID in delete");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing X-User-UUID header");
        }

        boolean deleted = objectService.delete(id, userUUID);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Object not found");
        }
        return ResponseEntity.noContent().build();
    }
}
