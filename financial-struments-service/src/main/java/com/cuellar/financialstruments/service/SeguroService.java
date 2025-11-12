package com.cuellar.financialstruments.service;

import com.cuellar.financialstruments.domain.*;
import com.cuellar.financialstruments.dto.*;
import com.cuellar.financialstruments.repository.SeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeguroService {
    @Autowired
    private SeguroRepository seguroRepository;

    @Transactional
    public SeguroResponse createSeguro(CreateSeguroRequest request) {
        Seguro entity = new Seguro(
                request.getUserUUID(),
                request.getTipoSeguro(),
                request.getAseguradora(),
                request.getNumeroPoliza(),
                request.getSumaAsegurada(),
                request.getPrimaMensual(),
                request.getFechaInicio(),
                request.getFechaVencimiento()
        );
        Seguro saved = seguroRepository.save(entity);
        return toResponse(saved);
    }

    public List<SeguroResponse> getAllSeguros() {
        return seguroRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public SeguroResponse getById(Long id) {
        Seguro s = seguroRepository.findById(id).orElseThrow(() -> new RuntimeException("Seguro no encontrado"));
        return toResponse(s);
    }

    public List<SeguroResponse> getByUserUUID(String userUUID) {
        return seguroRepository.findByUserUUID(userUUID).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        seguroRepository.deleteById(id);
    }

    private SeguroResponse toResponse(Seguro s) {
        SeguroResponse dto = new SeguroResponse();
        dto.setID(s.getID());
        dto.setUserUUID(s.getUserUUID());
        dto.setTipoSeguro(s.getTipoSeguro());
        dto.setAseguradora(s.getAseguradora());
        dto.setNumeroPoliza(s.getNumeroPoliza());
        dto.setSumaAsegurada(s.getSumaAsegurada());
        dto.setPrimaMensual(s.getPrimaMensual());
        dto.setFechaInicio(s.getFechaInicio());
        dto.setFechaVencimiento(s.getFechaVencimiento());
        dto.setActivo(s.getActivo());
        dto.setFechaCreacion(s.getFechaCreacion());
        return dto;
    }
}
