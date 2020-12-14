package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.PatientGroup;
import com.zainimtiaz.nagarro.wrapper.PatientGroupWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 10/23/2018.
 */
@Repository
public interface PatientGroupRepository extends JpaRepository<PatientGroup, Long> {


    @Query("SELECT new com.sd.his.wrapper.PatientGroupWrapper(pg.id,pg.createdOn,pg.updatedOn,pg.name,pg.description,pg.status,false ) " +
            "FROM PatientGroup pg")
    List<PatientGroupWrapper> findAllByCreatedOn(Pageable pageable);

    @Query("SELECT CASE WHEN COUNT (pg) > 0 THEN true ELSE false END " +
            "FROM com.sd.his.model.PatientGroup pg " +
            "WHERE pg.name=:name")
    boolean getPatientGroupByName(@Param("name") String name);

    @Query("SELECT CASE WHEN COUNT (pg) > 0 THEN true ELSE false END " +
            "FROM com.sd.his.model.PatientGroup pg " +
            "WHERE pg.name=:name AND pg.id<>:id")
    boolean getPatientGroupByNameAndIdNotEqual(@Param("id") Long id, @Param("name") String name);


    @Query("SELECT new com.sd.his.wrapper.PatientGroupWrapper(pg.id,pg.createdOn,pg.updatedOn,pg.name,pg.description,pg.status,false ) " +
            "FROM com.sd.his.model.PatientGroup pg " +
            "WHERE pg.id=:id")
    PatientGroupWrapper getPatientGroupById(@Param("id") Long id);

    @Query("SELECT new com.sd.his.wrapper.PatientGroupWrapper(pg.id,pg.createdOn,pg.updatedOn,pg.name,pg.description,pg.status,false ) " +
            "FROM com.sd.his.model.PatientGroup pg " +
            "WHERE pg.name LIKE CONCAT('%',:name,'%') ")
    List<PatientGroupWrapper> searchPatientGroupByParams(Pageable pageable, @Param("name") String name);

    @Query("SELECT new com.sd.his.wrapper.PatientGroupWrapper(pg) FROM PatientGroup pg")
    List<PatientGroupWrapper> findAllGroups();
}
