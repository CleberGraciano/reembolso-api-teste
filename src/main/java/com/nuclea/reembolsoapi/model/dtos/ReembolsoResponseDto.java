package com.nuclea.reembolsoapi.model.dtos;

import com.nuclea.reembolsoapi.model.Reembolso;
import com.nuclea.reembolsoapi.model.StatusReembolso;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
public class ReembolsoResponseDto {

    private String id;

    private String nome;

    private String matricula;

    private float valor;

    private String descricao;

    private StatusReembolso statusReembolso;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDateTime dataCadastroReembolso;


    public static ReembolsoResponseDto convertDto(Reembolso reembolso){
        ReembolsoResponseDto reembolsoResponseDto = new ReembolsoResponseDto();
        reembolsoResponseDto.setId(reembolso.getId());
        reembolsoResponseDto.setNome(reembolso.getNome());
        reembolsoResponseDto.setMatricula(reembolso.getMatricula());
        reembolsoResponseDto.setValor(reembolso.getValor());
        reembolsoResponseDto.setDescricao(reembolso.getDescricao());
        reembolsoResponseDto.setStatusReembolso(reembolso.getStatusReembolso());
        reembolsoResponseDto.setDataCadastroReembolso(reembolso.getDataCadastroReembolso());
        return reembolsoResponseDto;
    }


}
