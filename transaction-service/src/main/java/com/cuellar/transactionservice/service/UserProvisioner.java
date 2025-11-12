package com.cuellar.transactionservice.service;

import com.cuellar.transactionservice.domain.User;
import com.cuellar.transactionservice.repository.UserRepository;
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
                        User.builder().uuid(uuid).build()
                ));
    }
}
