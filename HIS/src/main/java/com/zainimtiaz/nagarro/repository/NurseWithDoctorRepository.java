package com.zainimtiaz.nagarro.repository;

import com.sd.his.model.*;
import com.zainimtiaz.nagarro.model.Doctor;
import com.zainimtiaz.nagarro.model.Nurse;
import com.zainimtiaz.nagarro.model.NurseWithDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @author    : waqas kamran
 * @Date      : 17-Apr-18
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.*
 * @FileName  : UserAuthAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Repository
public interface NurseWithDoctorRepository extends JpaRepository<NurseWithDoctor, Long> {

   void deleteAllByNurse(Nurse nurse);
   void deleteAllByNurse_Id(Long nurseId);
   void deleteAllByDoctor(Doctor doctor);
   void deleteAllByDoctor_Id(Long doctorId);
   @Query("select nwd.doctor from NurseWithDoctor nwd inner join nwd.nurse n where n.id=:id ")
   List<Doctor> findNurseWithDoctors(@Param("id") Long id);//id=>nurseId
}

