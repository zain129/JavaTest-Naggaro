package com.zainimtiaz.nagarro.repository;

import com.sd.his.model.*;
import com.zainimtiaz.nagarro.model.BranchDepartment;
import com.zainimtiaz.nagarro.model.Department;
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
public interface BranchDepartmentRepository extends JpaRepository<BranchDepartment, Long> {

 //   BranchDepartment findByBranchAndDepartment(Department dpt);
    BranchDepartment findByDepartment(Department department);
    List<BranchDepartment> findAllByDepartment(Department department);

    @Query("select b from BranchDepartment as bd inner join bd.department b where bd.branch.id=:id")
    List<Department> findByBranch(@Param("id") Long id);//write query here



    // List<Department> findAllByIdIn(List<Long> ids);
    List<BranchDepartment> getAllByBranch_id(Long  branchId);
//    List<BranchDepartment> getAllByBranch_idIn(List<Long>  branchId);
}

