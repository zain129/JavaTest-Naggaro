package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.PatientImageSetup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientImageRepository  extends JpaRepository<PatientImageSetup, Long> {

  //  PatientImageSetup findByName(String name);



     //List<PatientImageSetup> getAll();
}
