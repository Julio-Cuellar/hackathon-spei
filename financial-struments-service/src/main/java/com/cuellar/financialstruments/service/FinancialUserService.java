package com.cuellar.financialstruments.service;

import com.cuellar.financialstruments.domain.FinancialUser;
import com.cuellar.financialstruments.repository.FinancialUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FinancialUserService {

    @Autowired
    private FinancialUserRepository repository;

    @Transactional
    public void registerUser(String uuid) {
        if (repository.existsByUuid(uuid)) {
            log.warn("⚠️ Usuario {} ya existe en BD", uuid);
            return;
        }

        FinancialUser user = new FinancialUser(uuid);
        repository.save(user);

        log.info("💾 Usuario {} GUARDADO EN BD", uuid);
    }

    public boolean userExists(String uuid) {
        return repository.existsByUuid(uuid);
    }
}