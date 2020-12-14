package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Medication;
import com.zainimtiaz.nagarro.wrapper.MedicationWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 8/28/2018.
 */
@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    @Query("SELECT new com.sd.his.wrapper.MedicationWrapper(medication) " +
            "FROM Medication medication " +
            "WHERE medication.patient.id=:patientId" +
            " order by medication.createdOn desc ")
    List<MedicationWrapper> getPaginatedMedications(Pageable pageable, @Param("patientId") Long patientId);

    @Query("SELECT new com.sd.his.wrapper.MedicationWrapper(medication) FROM Medication medication WHERE medication.patient.id=:patientId")
    List<MedicationWrapper> countPaginatedMedications(@Param("patientId") Long patientId);

    @Query("SELECT new com.sd.his.wrapper.MedicationWrapper(medication) FROM Medication medication")
    List<MedicationWrapper> getMedications();

    @Query("SELECT new com.sd.his.wrapper.MedicationWrapper(medication) FROM Medication medication where medication.id=:id")
    MedicationWrapper getMedicationById(@Param("id") long medicationId);


    @Query("SELECT new com.sd.his.wrapper.MedicationWrapper(m) " +
            "FROM Medication m " +
            "WHERE m.patient.id=:patientId AND m.status=:status order by m.createdOn desc ")
    List<MedicationWrapper> getPaginatedMedicationsByStatusAndPatientId(Pageable pageable, @Param("status") String status, @Param("patientId") Long patientId);

    @Query("SELECT new com.sd.his.wrapper.MedicationWrapper(m) " +
            "FROM Medication m " +
            "WHERE m.patient.id=:patientId AND m.status=:status")
    List<MedicationWrapper> countPaginatedMedicationsByStatusAndPatientId(@Param("status") String status, @Param("patientId") Long patientId);


    @Query("SELECT medication " +
            "FROM Medication medication " +
            "WHERE medication.patient.id=:patientId" +
            " order by medication.createdOn desc ")
    List<Medication> getPaginatedMedicationsAt(Pageable pageable, @Param("patientId") Long patientId);
}
