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
        // Validar si el usuario está en buró
        if (buroCreditoRepository.findByUserUUID(request.getUserUUID()).isPresent())
            throw new RuntimeException("El usuario está en buró y no puede obtener un crédito.");

        Credito entity = new Credito(
                request.getUserUUID(),
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

    public CreditoResponse getById(Long id) {
        Credito c = creditoRepository.findById(id).orElseThrow(() -> new RuntimeException("Crédito no encontrado"));
        return toResponse(c);
    }

    public List<CreditoResponse> getByUserUUID(String userUUID) {
        return creditoRepository.findByUserUUID(userUUID).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        creditoRepository.deleteById(id);
    }

    private CreditoResponse toResponse(Credito c) {
        CreditoResponse dto = new CreditoResponse();
        dto.setID(c.getID());
        dto.setUserUUID(c.getUserUUID());
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
