package com.zainimtiaz.nagarro.repository;


import com.zainimtiaz.nagarro.model.Cashier;
import com.zainimtiaz.nagarro.model.User;
import com.zainimtiaz.nagarro.wrapper.response.StaffResponseWrapper;
import com.zainimtiaz.nagarro.wrapper.response.StaffWrapper;
import org.springframework.data.domain.Pageable;
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
public interface CashierRepository extends JpaRepository<Cashier, Long> {

    @Query("SELECT new com.sd.his.wrapper.response.StaffWrapper(du.id,cr.id,du.username,du.userType,cr.firstName,cr.lastName,cr.email,br.name) FROM Cashier cr INNER JOIN cr.user du INNER JOIN cr.branchCashiers branchCr INNER JOIN branchCr.branch  br  WHERE du.active = TRUE AND branchCr.primaryBranch=TRUE")
    List<StaffWrapper> findAllByActive(Pageable pageable);
    @Query("SELECT new com.sd.his.wrapper.response.StaffWrapper(du.id,cr.id,du.username,du.userType,cr.firstName,cr.lastName,cr.email,br.name) FROM Cashier cr INNER JOIN cr.user du INNER JOIN cr.branchCashiers branchCr INNER JOIN branchCr.branch  br  WHERE du.active = TRUE AND branchCr.primaryBranch=TRUE")
    List<StaffWrapper> findAllByActive();

 /*   Long uId,Long pId,String userType,String firstName,String lastName,String userName,
    String primaryBranch,String email,String homePhone,String cellPhone,String expiryDate,Boolean active,
    List<Branch> branchLi*/

    @Query("SELECT new com.sd.his.wrapper.response.StaffResponseWrapper(du.id,cr.id,du.userType,cr.firstName,cr.lastName,du.username,cr.email,br.name,cr.homePhone,cr.cellPhone,du.active,br.id,cr.accountExpiry,cr.sendBillingReport,cr.useReceiptDashboard,cr.otherDoctorDashboard,cr) FROM Cashier cr INNER JOIN cr.user du INNER JOIN cr.branchCashiers branchCr INNER JOIN branchCr.branch br WHERE cr.id =:id AND du.active = TRUE AND branchCr.primaryBranch=TRUE")
    StaffResponseWrapper findAllByIdAndStatusActive(@Param("id") Long id);
    Cashier findByUser(User user);

    @Query("SELECT new com.sd.his.wrapper.response.StaffWrapper(cru.id,ca.id,cru.username,cru.userType,ca.firstName,ca.lastName,ca.email,br.name) FROM Cashier ca INNER JOIN ca.user cru INNER JOIN ca.branchCashiers branchCr INNER JOIN branchCr.branch br WHERE (lower( ca.firstName ) LIKE concat('%',:name,'%') or lower( ca.lastName ) LIKE concat('%',:name,'%') OR cru.userType=:userType) AND cru.active = TRUE AND branchCr.primaryBranch=TRUE")
    List<StaffWrapper> findAllBySearchCriteria(@Param("name") String name, @Param("userType") String userType, Pageable pageable);
}