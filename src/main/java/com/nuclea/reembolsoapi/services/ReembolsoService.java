package com.nuclea.reembolsoapi.services;


import com.nuclea.reembolsoapi.model.StatusReembolso;
import com.nuclea.reembolsoapi.model.dtos.ReembolsoRequestDto;
import com.nuclea.reembolsoapi.model.dtos.ReembolsoResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Stream;


public interface ReembolsoService {

    Object solicitarReembolso(ReembolsoRequestDto reembolso);
    ResponseEntity<Object> acompanharReembolsoPorId(String id);
    ResponseEntity<Object>  cancelarReembolso(String id);
    ResponseEntity<Object> alterarStatusReembolso(String idReembolso, StatusReembolso statusReembolso);
    List<ReembolsoResponseDto> listarTodosReembolsosPorMatricula(String matricula);

    List<ReembolsoResponseDto> listarTodosReembolsos();

}
