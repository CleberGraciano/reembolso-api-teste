package com.nuclea.reembolsoapi.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nuclea.reembolsoapi.model.Reembolso;
import com.nuclea.reembolsoapi.model.StatusReembolso;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ReembolsoResponseConsultaDto {

    private String codigo;

    @JsonIgnore
    private String nome;

    @JsonIgnore
    private String matricula;

    private float valor;

    private String descricao;

    private StatusReembolso statusReembolso;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @JsonIgnore
    private LocalDateTime dataCadastroReembolso;


    public static ReembolsoResponseConsultaDto convertDto(Reembolso reembolso){
        ReembolsoResponseConsultaDto reembolsoResponseConsultaDto = new ReembolsoResponseConsultaDto();
        reembolsoResponseConsultaDto.setCodigo(reembolso.getId());
        reembolsoResponseConsultaDto.setNome(reembolso.getNome());
        reembolsoResponseConsultaDto.setMatricula(reembolso.getMatricula());
        reembolsoResponseConsultaDto.setValor(reembolso.getValor());
        reembolsoResponseConsultaDto.setDescricao(reembolso.getDescricao());
        reembolsoResponseConsultaDto.setStatusReembolso(reembolso.getStatusReembolso());
        reembolsoResponseConsultaDto.setDataCadastroReembolso(reembolso.getDataCadastroReembolso());
        return reembolsoResponseConsultaDto;
    }


}
