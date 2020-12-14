package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Branch;
import com.zainimtiaz.nagarro.model.BranchDoctor;
import com.zainimtiaz.nagarro.model.Doctor;
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
public interface BranchDoctorRepository extends JpaRepository<BranchDoctor, Long> {

     BranchDoctor findByBranch(Branch branch);
     BranchDoctor findByDoctorAndPrimaryBranchTrue(Doctor doctor);
     void deleteAllByDoctorAndPrimaryBranchFalse(Doctor doctor);
     void deleteAllByDoctor(Doctor doctor);
     @Query("select b from BranchDoctor bd inner join bd.branch b where bd.doctor.id=:id")
     List<Branch> getDoctorBranches(@Param("id") Long id);//id=>doctor pk id
     @Query("select b from BranchDoctor bd inner join bd.doctor b where bd.branch.id=:id")
     List<Doctor> getBranchesDoctors(@Param("id") Long id);
}

