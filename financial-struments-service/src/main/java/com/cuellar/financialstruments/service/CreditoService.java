package com.cuellar.financialstruments.service;

import com.cuellar.financialstruments.domain.*;
import com.cuellar.financialstruments.dto.*;
import com.cuellar.financialstruments.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditoService {
    @Autowired
    private CreditoRepository creditoRepository;
    @Autowired
    private BuroCreditoRepository buroCreditoRepository;

    @Transactional
    public CreditoResponse createCredito(CreateCreditoRequest request) {
        // Si el usuario está en buró, no puede tener crédito
        if (buroCreditoRepository.findByCuentaUuid(request.getCuentaUuid()).isPresent())
            throw new RuntimeException("La cuenta está en buró y no puede obtener un crédito.");
        Credito entity = new Credito(
                request.getCuentaUuid(),
                request.getBanco(),
                request.getMonto(),
                request.getFechaCorte(),
                request.getFechaPago(),
                request.getTasaInteres()
        );
        Credito saved = creditoRepository.save(entity);
        return toResponse(saved);
    }

    public List<CreditoResponse> getAllCreditos() {
        return creditoRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public CreditoResponse getByUuid(String uuid) {
        Credito c = creditoRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("Crédito no encontrado"));
        return toResponse(c);
    }

    @Transactional
    public void deleteByUuid(String uuid) {
        creditoRepository.findByUuid(uuid)
                .ifPresent(creditoRepository::delete);
    }

    private CreditoResponse toResponse(Credito c) {
        CreditoResponse dto = new CreditoResponse();
        dto.setUuid(c.getUuid());
        dto.setCuentaUuid(c.getCuentaUuid());
        dto.setBanco(c.getBanco());
        dto.setMonto(c.getMonto());
        dto.setFechaCorte(c.getFechaCorte());
        dto.setFechaPago(c.getFechaPago());
        dto.setTasaInteres(c.getTasaInteres());
        dto.setStatus(c.getStatus().name());
        dto.setFechaCreacion(c.getFechaCreacion());
        return dto;
    }
}
