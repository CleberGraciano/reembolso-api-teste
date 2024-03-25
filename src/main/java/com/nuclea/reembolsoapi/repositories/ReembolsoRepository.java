package com.nuclea.reembolsoapi.repositories;

import com.nuclea.reembolsoapi.model.Reembolso;
import com.nuclea.reembolsoapi.model.StatusReembolso;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReembolsoRepository extends MongoRepository<Reembolso, String> {
     List<Reembolso> findByMatricula(String matricula);

     Reembolso findByIdAndMatriculaAndStatusReembolso(String idReembolso, String matricula, StatusReembolso statusReembolso);


}
