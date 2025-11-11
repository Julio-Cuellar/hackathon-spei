package com.cuellar.crudservice.service;

import com.cuellar.crudservice.domain.User;
import com.cuellar.crudservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserProvisioner {

    private final UserRepository userRepository;

    @Transactional
    public void ensureUser(String uuid) {
        userRepository.findByUserUUID(uuid)
                .orElseGet(() -> userRepository.save(
                        User.builder().userUUID(uuid).build()
                ));
    }
}
