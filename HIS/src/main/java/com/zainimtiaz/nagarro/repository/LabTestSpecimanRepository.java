package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.LabTestSpeciman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestSpecimanRepository extends JpaRepository<LabTestSpeciman, Long> {

    @Query("SELECT new com.sd.his.model.LabTestSpeciman(labTestSpeciman) FROM LabTestSpeciman labTestSpeciman")
    List<LabTestSpeciman> getAll();

    @Query("SELECT new com.sd.his.model.LabTestSpeciman(lts) FROM LabTestSpeciman lts " +
            " WHERE lts.testCode = :testCode AND lts.testName = :testName AND lts.minNormalRange = :minNormalRange " +
            " AND lts.maxNormalRange = :maxNormalRange ")
    LabTestSpeciman findDuplicateTestEntry(@Param("testCode") String testCode, @Param("testName") String testName,
                                           @Param("minNormalRange") String minNormalRange, @Param("maxNormalRange") String maxNormalRange);


    @Query("SELECT CASE WHEN COUNT (labTestSpeciman) > 0 THEN true ELSE false END " +
            "FROM com.sd.his.model.LabTestSpeciman labTestSpeciman " +
            "WHERE labTestSpeciman.testName=:name AND labTestSpeciman.id<>:id")
    boolean getNameAndEqualId(@Param("id") Long id, @Param("name") String name);



    @Query("SELECT new com.sd.his.model.LabTestSpeciman(lts) FROM LabTestSpeciman lts " +
            " WHERE  lts.testCode = :testName  ")
    LabTestSpeciman findTestEntry(@Param("testName") String testName);
}
