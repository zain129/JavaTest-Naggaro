package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Zone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {




    @Query("SELECT t FROM Zone t where t.name = :name")
    Zone findZoneByName(@Param("name") String paraName);

}