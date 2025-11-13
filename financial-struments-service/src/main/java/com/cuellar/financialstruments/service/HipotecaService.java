package com.cuellar.financialstruments.service;

import com.cuellar.financialstruments.domain.*;
import com.cuellar.financialstruments.dto.*;
import com.cuellar.financialstruments.repository.HipotecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HipotecaService {
    @Autowired
    private HipotecaRepository hipotecaRepository;

    @Transactional
    public HipotecaResponse createHipoteca(CreateHipotecaRequest request) {
        Hipoteca entity = new Hipoteca(
                request.getUuid(),
                request.getCuentaUuid(),
                request.getBanco(),
                request.getMonto(),
                request.getPlazoMeses()
        );
        Hipoteca saved = hipotecaRepository.save(entity);
        return toResponse(saved);
    }

    public List<HipotecaResponse> getAllHipotecas() {
        return hipotecaRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public HipotecaResponse getByUuid(String uuid) {
        Hipoteca h = hipotecaRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("Hipoteca no encontrada"));
        return toResponse(h);
    }

    @Transactional
    public void deleteByUuid(String uuid) {
        hipotecaRepository.findByUuid(uuid).ifPresent(hipotecaRepository::delete);
    }

    private HipotecaResponse toResponse(Hipoteca h) {
        HipotecaResponse dto = new HipotecaResponse();
        dto.setUuid(h.getUuid());
        dto.setCuentaUuid(h.getCuentaUuid());
        dto.setBanco(h.getBanco());
        dto.setMonto(h.getMonto());
        dto.setPlazoMeses(h.getPlazoMeses());
        dto.setActivo(h.getActivo());
        dto.setFechaCreacion(h.getFechaCreacion());
        return dto;
    }
}