package com.nuclea.reembolsoapi.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nuclea.reembolsoapi.model.Reembolso;
import com.nuclea.reembolsoapi.model.StatusReembolso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Data
public class ReembolsoRequestDto {

    @JsonIgnore
    private String id;

    @NotBlank(message = "O Campo nome não pode ser vazio.")
    @NotNull(message = "O Campo nome não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O Campo matricula não pode ser vazio.")
    @NotNull(message = "O Campo matricula não pode ser vazio.")
    private String matricula;

    @NotNull(message = "O Campo valor não pode ser vazio.")
    private float valor;

    private String descricao;

    @JsonIgnore
    private StatusReembolso statusReembolso;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @JsonIgnore
    private LocalDateTime dataCadastroReembolso;


    public static ReembolsoRequestDto convertDto(Reembolso reembolso){
        ReembolsoRequestDto reembolsoRequestDto = new ReembolsoRequestDto();
        reembolsoRequestDto.setId(reembolso.getId());
        reembolsoRequestDto.setNome(reembolso.getNome());
        reembolsoRequestDto.setMatricula(reembolso.getMatricula());
        reembolsoRequestDto.setValor(reembolso.getValor());
        reembolsoRequestDto.setDescricao(reembolso.getDescricao());
        reembolsoRequestDto.setStatusReembolso(reembolso.getStatusReembolso());
        reembolsoRequestDto.setDataCadastroReembolso(reembolso.getDataCadastroReembolso());
        return reembolsoRequestDto;
    }
}
