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
                request.getUserUUID(),
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

    public HipotecaResponse getById(Long id) {
        Hipoteca h = hipotecaRepository.findById(id).orElseThrow(() -> new RuntimeException("Hipoteca no encontrada"));
        return toResponse(h);
    }

    public List<HipotecaResponse> getByUserUUID(String userUUID) {
        return hipotecaRepository.findByUserUUID(userUUID).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        hipotecaRepository.deleteById(id);
    }

    private HipotecaResponse toResponse(Hipoteca h) {
        HipotecaResponse dto = new HipotecaResponse();
        dto.setID(h.getID());
        dto.setUserUUID(h.getUserUUID());
        dto.setBanco(h.getBanco());
        dto.setMonto(h.getMonto());
        dto.setPlazoMeses(h.getPlazoMeses());
        dto.setActivo(h.getActivo());
        dto.setFechaCreacion(h.getFechaCreacion());
        return dto;
    }
}
