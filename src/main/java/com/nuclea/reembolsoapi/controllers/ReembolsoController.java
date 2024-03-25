package com.nuclea.reembolsoapi.controllers;

import com.nuclea.reembolsoapi.model.Reembolso;
import com.nuclea.reembolsoapi.model.StatusReembolso;
import com.nuclea.reembolsoapi.model.dtos.CancelamentoDto;
import com.nuclea.reembolsoapi.model.dtos.ReembolsoRequestDto;
import com.nuclea.reembolsoapi.model.dtos.ReembolsoResponseConsultaDto;
import com.nuclea.reembolsoapi.model.dtos.ReembolsoResponseDto;
import com.nuclea.reembolsoapi.services.ReembolsoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/reembolsos/v1")
public class ReembolsoController {

    private ReembolsoService reembolsoService;

    @Autowired
    public ReembolsoController(ReembolsoService reembolsoService) {
        this.reembolsoService = reembolsoService;
    }

    @PostMapping()
    public Object solicitarReembolso(@RequestBody @Valid ReembolsoRequestDto reembolso){
        return reembolsoService.solicitarReembolso(reembolso);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> consultarReembolso(@PathVariable String id) {
        return reembolsoService.acompanharReembolsoPorId(id);
    }

    @GetMapping("/matricula/{matricula}")
    public List<ReembolsoResponseDto>consultarTodosReembolsosPorMatricula(@PathVariable String matricula){
        return reembolsoService.listarTodosReembolsosPorMatricula(matricula);
    }


    @DeleteMapping("/{idReembolso}")
    public ResponseEntity<Object>  cancelarReembolso(@PathVariable String idReembolso){
        return reembolsoService.cancelarReembolso(idReembolso);
    }

    @PutMapping("/{idReembolso}")
    public ResponseEntity<Object>alterarStatusReembolso(@PathVariable String idReembolso, @RequestParam StatusReembolso statusReembolso){
        return reembolsoService.alterarStatusReembolso(idReembolso, statusReembolso);
    }

    @GetMapping
    List<ReembolsoResponseDto> listarTodosReembolsos(){
        return reembolsoService.listarTodosReembolsos();
    }


}
