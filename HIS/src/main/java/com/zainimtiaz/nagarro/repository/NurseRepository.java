package com.zainimtiaz.nagarro.repository;


import com.zainimtiaz.nagarro.model.Nurse;
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
public interface NurseRepository extends JpaRepository<Nurse, Long> {

    @Query("SELECT new com.sd.his.wrapper.response.StaffWrapper(du.id,nr.id,du.username,du.userType,nr.firstName,nr.lastName,nr.email,br.name) FROM Nurse nr INNER JOIN nr.user du INNER JOIN nr.branchNurses branchNr INNER JOIN branchNr.branch  br  WHERE du.active = TRUE AND branchNr.primaryBranch=TRUE")
    List<StaffWrapper> findAllByActive(Pageable pageable);
    @Query("SELECT new com.sd.his.wrapper.response.StaffWrapper(du.id,nr.id,du.username,du.userType,nr.firstName,nr.lastName,nr.email,br.name) FROM Nurse nr INNER JOIN nr.user du INNER JOIN nr.branchNurses branchNr INNER JOIN branchNr.branch  br  WHERE du.active = TRUE AND branchNr.primaryBranch=TRUE")
    List<StaffWrapper> findAllByActive();

    @Query("SELECT new com.sd.his.wrapper.response.StaffResponseWrapper(du.id,nr.id,du.userType,nr.firstName,nr.lastName,du.username,nr.email,br.name,nr.homePhone,nr.cellPhone,du.active,br.id,nr.accountExpiry,nr.sendBillingReport,nr.useReceiptDashboard,nr.otherDoctorDashboard,nr.managePatientRecords,nr.managePatientInvoices,nr) FROM Nurse nr INNER JOIN nr.user du INNER JOIN nr.branchNurses branchCr INNER JOIN branchCr.branch br WHERE nr.id =:id AND du.active = TRUE AND branchCr.primaryBranch=true ")
    StaffResponseWrapper findAllByIdAndStatusActive(@Param("id") Long id);

    Nurse findByUser(User user);

    @Query("SELECT distinct new com.sd.his.wrapper.response.StaffWrapper(nu.id,n.id,nu.username,nu.userType,n.firstName,n.lastName,n.email,br.name) FROM Nurse n INNER JOIN n.user nu INNER JOIN n.branchNurses bn INNER JOIN bn.branch br WHERE (lower( n.firstName ) LIKE concat('%',:name,'%') or lower( n.lastName ) LIKE concat('%',:name,'%') OR nu.userType=:userType ) AND nu.active = TRUE AND bn.primaryBranch=TRUE")
    List<StaffWrapper> findAllBySearchCriteria(@Param("name") String name, @Param("userType") String userType, Pageable pageable);
}

