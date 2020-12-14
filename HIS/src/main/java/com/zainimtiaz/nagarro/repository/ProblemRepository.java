package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Problem;
import com.zainimtiaz.nagarro.wrapper.ProblemWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by jamal on 8/15/2018.
 */
@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query("SELECT NEW com.sd.his.wrapper.ProblemWrapper(problem.id,problem.createdOn,problem.updatedOn," +
            "problem.appointment.id," +
            "problem.icdCode.id,problem.icdCode.code," +
            "problem.icdVersion.id,problem.icdVersion.name," +
            "problem.dateDiagnosis,problem.note,problem.status," +
            "problem.info," +
            "problem.patient.id) " +
            "FROM Problem problem WHERE problem.patient.id=:patientId order by problem.createdOn desc ")
    Page<ProblemWrapper> findAllByPatient_idOrderByCreatedOnDesc(Pageable page, @Param("patientId") Long patientId);

    @Query("SELECT NEW com.sd.his.wrapper.ProblemWrapper(problem.id,problem.createdOn,problem.updatedOn," +
            "problem.appointment.id," +
            "problem.icdCode.id,problem.icdCode.code," +
            "problem.icdVersion.id,problem.icdVersion.name," +
            "problem.dateDiagnosis,problem.note,problem.status," +
            "problem.info," +
            "problem.patient.id) " +
            "FROM Problem problem WHERE problem.patient.id=:patientId AND problem.status=:status")
    Page<ProblemWrapper> findAllByStatusAndPatient_id(Pageable page, @Param("status") String status, @Param("patientId") Long patientId);

    @Query("SELECT NEW com.sd.his.wrapper.ProblemWrapper(problem.id,problem.createdOn,problem.updatedOn," +
            "problem.appointment.id," +
            "problem.icdCode.id,problem.icdCode.code," +
            "problem.icdVersion.id,problem.icdVersion.name," +
            "problem.dateDiagnosis,problem.note,problem.status," +
            "problem.info," +
            "problem.patient.id) " +
            "FROM Problem problem WHERE problem.id=:id ")
    ProblemWrapper getProblemById(@Param("id") long id);
}
