package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Allergy;
import com.zainimtiaz.nagarro.wrapper.AllergyWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by jamal on 8/20/2018.
 */
public interface AllergyRepository extends JpaRepository<Allergy, Long> {


    @Query("SELECT new com.sd.his.wrapper.AllergyWrapper(allergy) FROM Allergy allergy where allergy.id=:id")
    AllergyWrapper getAllergyById(@Param("id") long allergyId);

    @Query("SELECT new com.sd.his.wrapper.AllergyWrapper(allergy) " +
            "FROM Allergy allergy " +
            "WHERE allergy.patient.id=:patientId order by allergy.createdOn desc ")
    List<AllergyWrapper> getPaginatedAllergies(Pageable pageable,@Param("patientId") Long patientId);

    int countAllByPatient_Id(Long patientId);

    @Query("SELECT new com.sd.his.wrapper.AllergyWrapper(allergy) FROM Allergy allergy")
    List<AllergyWrapper> getAllAllergies();

    @Query("SELECT new com.sd.his.wrapper.AllergyWrapper(allergy) " +
            "FROM Allergy allergy " +
            "WHERE allergy.patient.id=:patientId AND allergy.status=:status")
    List<AllergyWrapper> getAllAllergiesByStatusAndPatientId(Pageable pageable, @Param("status") String status, @Param("patientId") Long patientId);

    @Query("SELECT new com.sd.his.wrapper.AllergyWrapper(allergy) " +
            "FROM Allergy allergy " +
            "WHERE allergy.patient.id=:patientId AND allergy.status=:status")
    List<AllergyWrapper> countAllAllergiesByStatusAndPatientId(@Param("status") String status, @Param("patientId") Long patientId);


}
