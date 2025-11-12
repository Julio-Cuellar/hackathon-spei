package com.cuellar.financialstruments.service;

import com.cuellar.financialstruments.domain.BuroCredito;
import com.cuellar.financialstruments.repository.BuroCreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuroCreditoService {
    @Autowired
    private BuroCreditoRepository buroCreditoRepository;

    public boolean isEnBuro(String userUUID) {
        return buroCreditoRepository.findByUserUUID(userUUID).isPresent();
    }

    @Transactional
    public void marcarEnBuro(String userUUID, String rfc) {
        if (!isEnBuro(userUUID)) {
            buroCreditoRepository.save(new BuroCredito(userUUID, rfc));
        }
    }

    @Transactional
    public void borrarDeBuro(String userUUID) {
        buroCreditoRepository.findByUserUUID(userUUID)
                .ifPresent(buroCreditoRepository::delete);
    }
}
