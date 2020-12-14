package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.VitalSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VitalSetupRepository  extends JpaRepository<VitalSetup, Long> {

    VitalSetup findByName(String name);


    @Query("SELECT new com.sd.his.model.VitalSetup(vitalSetup) FROM VitalSetup vitalSetup")
    List<VitalSetup> getAll();
}
