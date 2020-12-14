package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.PatientVital;
import com.zainimtiaz.nagarro.wrapper.PatientVitalWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PatientVitalRepository  extends JpaRepository<PatientVital, Long> {

    PatientVital findByName(String name);

  /*  @Query("SELECT new com.sd.his.model.PatientVital(patientVital) FROM PatientVital patientVital")
    List<PatientVital> getAll();*/

//    @Query("SELECT new com.sd.his.wrapper.PatientVitalWrapper(patientVital.id,patientVital.name, patientVital.unit, patientVital.standardValue, patientVital.currentValue, patientVital.status, patientVital.patient, patientVital.updatedOn) FROM PatientVital patientVital where patientVital.status = 'true' and patientVital.patient.id=:patientId ")
//    List<PatientVital> getAllVitalPatient();

    @Query("SELECT new com.sd.his.wrapper.PatientVitalWrapper(patientVital.id,patientVital.name, patientVital.unit, patientVital.standardValue, patientVital.currentValue, patientVital.status, patientVital.patient, patientVital.updatedOn,patientVital.chiefComplaint,patientVital.dateVital,patientVital.appointment) FROM PatientVital patientVital")
    List<PatientVitalWrapper> getAll();

    @Query("Select new com.sd.his.wrapper.PatientVitalWrapper(patientVital.id,patientVital.name, patientVital.unit, patientVital.standardValue, patientVital.currentValue, patientVital.status, patientVital.patient, patientVital.updatedOn,patientVital.chiefComplaint,patientVital.dateVital,patientVital.appointment) FROM PatientVital patientVital where  patientVital.patient.id=:patientId")
    List<PatientVitalWrapper> getPaginatedOrder(Pageable pageable, @Param("patientId") Long patientId);


    @Query("Select new com.sd.his.wrapper.PatientVitalWrapper(patientVital.id,patientVital.name, patientVital.unit, patientVital.standardValue, patientVital.currentValue, patientVital.status, patientVital.patient, patientVital.updatedOn,patientVital.chiefComplaint,patientVital.dateVital,patientVital.appointment) FROM PatientVital patientVital where  patientVital.patient.id=:patientId ORDER BY patientVital.appointment DESC")
    List<PatientVitalWrapper> getPaginatedOrderSort(Pageable pageable, @Param("patientId") Long patientId);

}