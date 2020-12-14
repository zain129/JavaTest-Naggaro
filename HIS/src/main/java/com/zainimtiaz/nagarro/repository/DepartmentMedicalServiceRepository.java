package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.DepartmentMedicalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jamal on 7/31/2018.
 */
@Repository
public interface DepartmentMedicalServiceRepository extends JpaRepository<DepartmentMedicalService,Long>{
    void deleteByMedicalService_id(Long medicalServiceId);

}
