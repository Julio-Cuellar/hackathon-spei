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

    public boolean isEnBuro(String cuentaUuid) {
        return buroCreditoRepository.findByCuentaUuid(cuentaUuid).isPresent();
    }

    @Transactional
    public void marcarEnBuro(String cuentaUuid) {
        if (!isEnBuro(cuentaUuid)) buroCreditoRepository.save(new BuroCredito(cuentaUuid));
    }

    @Transactional
    public void borrarDeBuro(String cuentaUuid) {
        buroCreditoRepository.findByCuentaUuid(cuentaUuid)
                .ifPresent(buroCreditoRepository::delete);
    }
}
