package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Branch;
import com.zainimtiaz.nagarro.model.BranchNurse;
import com.zainimtiaz.nagarro.model.Nurse;
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
public interface BranchNurseRepository extends JpaRepository<BranchNurse, Long> {


     BranchNurse findByBranch(Branch branch);
     BranchNurse findByNurse(Nurse nurse);
     void deleteAllByNurse(Nurse nurse);
     void deleteAllByNurseAndPrimaryBranchFalse(Nurse nurse);
     BranchNurse findByNurseAndPrimaryBranchTrue(Nurse nurse);

     @Query("select b from BranchNurse as bn inner join bn.branch b where bn.nurse.id=:id")
     List<Branch> getNurseBranches(@Param("id") Long id);//id=>nurse pk id
}

