package com.nuclea.reembolsoapi.services.impl;


import com.mongodb.client.MongoClient;
import com.nuclea.reembolsoapi.model.Reembolso;
import com.nuclea.reembolsoapi.model.ResultadoAgregacao;
import com.nuclea.reembolsoapi.model.StatusReembolso;
import com.nuclea.reembolsoapi.model.dtos.CancelamentoDto;
import com.nuclea.reembolsoapi.model.dtos.ReembolsoRequestDto;
import com.nuclea.reembolsoapi.model.dtos.ReembolsoResponseConsultaDto;
import com.nuclea.reembolsoapi.model.dtos.ReembolsoResponseDto;
import com.nuclea.reembolsoapi.repositories.ReembolsoRepository;
import com.nuclea.reembolsoapi.services.ReembolsoService;

import com.nuclea.reembolsoapi.util.ValidaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReembolsoServiceImpl implements ReembolsoService {

    private ReembolsoRepository reembolsoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoClient mongoClient;

    private static final Logger logger = LoggerFactory.getLogger(ReembolsoServiceImpl.class);

    private int DIA_INICIO = 1;
    private int DIA_FIM = 20;


    @Autowired
    public ReembolsoServiceImpl(ReembolsoRepository reembolsoRepository) {
        this.reembolsoRepository = reembolsoRepository;
    }

    @Override
    public ResponseEntity<Object> solicitarReembolso(ReembolsoRequestDto reembolsoRequestDto) {
        ResultadoAgregacao resultadoAgregacao = new ResultadoAgregacao();
        resultadoAgregacao.setTotal(this.totalValorReembolsosRangeDatas(reembolsoRequestDto.getMatricula()));
        reembolsoRequestDto.setDataCadastroReembolso(LocalDateTime.now());

        if (!ValidaData.validarDiaMes(DIA_INICIO,DIA_FIM, reembolsoRequestDto.getDataCadastroReembolso())){
            logger.info("Data fora do periodo para criar Reembolso, favor solicitar reembolso entre os dias 1 - 20 ");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data fora do periodo para criar Reembolso, favor solicitar reembolso entre os dias 1 -20");
        }
        if ( resultadoAgregacao.getTotal()+reembolsoRequestDto.getValor()> 500){
            var valorRestante = 500 - resultadoAgregacao.getTotal();
            logger.info("Valor de reembolso ultrapassou 500 reais");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Valor de reembolso ultrapassou 500 reais, valor disponivél para reembolso é: "+valorRestante);
        }else {
            reembolsoRequestDto.setId(UUID.randomUUID().toString());
            reembolsoRequestDto.setStatusReembolso(StatusReembolso.SOLICITACAO_EM_ANALISE);

            logger.info("Reembolso criado com sucesso "+reembolsoRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ReembolsoResponseDto.convertDto(reembolsoRepository.save(Reembolso.convertEntityRequest(reembolsoRequestDto))));
        }
    }

    @Override
    public ResponseEntity<Object> acompanharReembolsoPorId(String id) {
        Optional<Reembolso> reembolso = reembolsoRepository.findById(id);
        if (reembolso.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(ReembolsoResponseDto.convertDto(reembolso.get()));
        }else {
            logger.info("Reembolso não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reembolso não encontrado");
        }

    }

    @Override
    public ResponseEntity<Object> cancelarReembolso(String id) {
        CancelamentoDto cancelamentoDto = new CancelamentoDto();
        Optional<Reembolso> reembolso = reembolsoRepository.findById(id);
               if (reembolso.isPresent()){
                   cancelamentoDto.setCodigo(reembolso.get().getId());
                   logger.info("Reembolso solicitado erroneamente.");
                   cancelamentoDto.setMotivo("Reembolso solicitado erroneamente.");
                   reembolsoRepository.deleteById(id);
                   return ResponseEntity.status(HttpStatus.OK).body(cancelamentoDto);
               }else {
                   logger.info("Reembolso não encontrado");
                   return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reembolso não encontrado");
               }
    }

    @Override
    public ResponseEntity<Object> alterarStatusReembolso(String idReembolso, StatusReembolso statusReembolso) {
        Optional<Reembolso> reembolso = reembolsoRepository.findById(idReembolso);
        if (!reembolso.isPresent()){
            logger.info("Reembolso não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reembolso não encontrado");
        } else {
            if(reembolsoRepository.findByIdAndMatriculaAndStatusReembolso(idReembolso, reembolso.get().getMatricula(), statusReembolso)==null){
                reembolso.get().setStatusReembolso(statusReembolso);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(ReembolsoResponseConsultaDto.convertDto(reembolsoRepository.save(reembolso.get())));
            }else {
                logger.info("Reembolso Já possui o status"+statusReembolso);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reembolso Já possui o status "+statusReembolso);
            }

        }
    }

    @Override
    public List<ReembolsoResponseDto> listarTodosReembolsosPorMatricula(String matricula){
        System.out.printf("Total reembolso por mês: "+this.totalValorReembolsosRangeDatas(matricula));
        return reembolsoRepository.findByMatricula(matricula).stream().map(reembolso -> ReembolsoResponseDto.convertDto(reembolso)).toList();
    }

    public List<ReembolsoResponseDto> listarTodosReembolsos() {
        return reembolsoRepository.findAll().stream().map(reembolso -> ReembolsoResponseDto.convertDto(reembolso)).toList();
    }

    private float somaValorReembolso(String matricula) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(
                        Criteria.where("matricula").is(matricula)
                ),
                Aggregation.group().sum("valor").as("total")
        );

        AggregationResults<ResultadoAgregacao> results = mongoTemplate.aggregate(aggregation, "reembolso", ResultadoAgregacao.class);
        ResultadoAgregacao resultadoAgregacao = results.getUniqueMappedResult();
        return resultadoAgregacao != null ? resultadoAgregacao.getTotal() : 0;

    }

    private float totalValorReembolsosRangeDatas(String matricula){
        List<Reembolso> reembolsos = reembolsoRepository.findByMatricula(matricula);
        List<Reembolso> reembolsosDentroData = new ArrayList<>();
        float total = 0;

        reembolsos.forEach(reembolso -> {
            if (ValidaData.validarDiaMes(DIA_INICIO,DIA_FIM, reembolso.getDataCadastroReembolso())){
                reembolsosDentroData.add(reembolso);
            }
        });
        for (Reembolso reembolso : reembolsosDentroData) {
            total += reembolso.getValor();
        }
        return total;
    }


//    private boolean validaTotalReembolsoMes(ReembolsoRequestDto reembolsoRequestDto){
//        ResultadoAgregacao resultadoAgregacao = new ResultadoAgregacao();
//        if (ValidaData.validarDiaMes(1, 20, reembolsoRequestDto.getDataCadastroReembolso())){
//            if(resultadoAgregacao.getTotal()+reembolsoRequestDto.getValor()> 500){
//                return false;
//            }
//        }
//        return true;
//    }
}
