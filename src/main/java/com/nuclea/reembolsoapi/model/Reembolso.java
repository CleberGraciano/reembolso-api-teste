package com.nuclea.reembolsoapi.model;

import com.nuclea.reembolsoapi.model.dtos.ReembolsoRequestDto;
import com.nuclea.reembolsoapi.model.dtos.ReembolsoResponseDto;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Document(collection = "reembolso")
public class Reembolso {


    private String id;

    private String nome;

    private String matricula;

    private float valor;

    private String descricao;

    private StatusReembolso statusReembolso;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDateTime dataCadastroReembolso;


    public static Reembolso convertEntityRequest( ReembolsoRequestDto reembolsoRequestDto){
        Reembolso reembolso = new Reembolso();
        reembolso.setId(reembolsoRequestDto.getId());
        reembolso.setNome(reembolsoRequestDto.getNome());
        reembolso.setMatricula(reembolsoRequestDto.getMatricula());
        reembolso.setValor(reembolsoRequestDto.getValor());
        reembolso.setDescricao(reembolsoRequestDto.getDescricao());
        reembolso.setStatusReembolso(reembolsoRequestDto.getStatusReembolso());
        reembolso.setDataCadastroReembolso(reembolsoRequestDto.getDataCadastroReembolso());
        return reembolso;
    }

    public static Reembolso convertEntityResponse( ReembolsoResponseDto reembolsoResponseDto){
        Reembolso reembolso = new Reembolso();
        reembolso.setId(reembolsoResponseDto.getId());
        reembolso.setNome(reembolsoResponseDto.getNome());
        reembolso.setMatricula(reembolsoResponseDto.getMatricula());
        reembolso.setValor(reembolsoResponseDto.getValor());
        reembolso.setStatusReembolso(reembolsoResponseDto.getStatusReembolso());
        reembolso.setDataCadastroReembolso(reembolsoResponseDto.getDataCadastroReembolso());
        return reembolso;
    }


}
