package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Branch;
import com.zainimtiaz.nagarro.model.BranchCashier;
import com.zainimtiaz.nagarro.model.Cashier;
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
public interface BranchCashierRepository extends JpaRepository<BranchCashier, Long> {


     BranchCashier findByBranch(Branch branch);
     BranchCashier findByCashier(Cashier cashier);
     void deleteAllByCashier(Cashier cashier);
     BranchCashier findByCashierAndPrimaryBranchTrue(Cashier cashier);
     void deleteAllByCashierAndPrimaryBranchFalse(Cashier cashier);
     @Query("select b from BranchCashier bc inner join bc.branch b where bc.cashier.id=:id")
     List<Branch> getCashierBranches(@Param("id") Long id);//id=>cashier pk id
}

