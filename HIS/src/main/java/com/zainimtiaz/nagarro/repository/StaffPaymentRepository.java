package com.zainimtiaz.nagarro.repository;


import com.zainimtiaz.nagarro.model.StaffPayment;
import com.zainimtiaz.nagarro.wrapper.request.DoctorPaymentRequestWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/*
 * @author    : Naeem Saeed
 * @Date      : 6/12/2018
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer    Date       Version  Operation  Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.repositories
 * @FileName  : ClinicalDepartmentRepository
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Repository
public interface StaffPaymentRepository extends JpaRepository<StaffPayment, Long> {


    @Query(" SELECT NEW com.sd.his.wrapper.request.DoctorPaymentRequestWrapper(sp) FROM StaffPayment sp ")
    List<DoctorPaymentRequestWrapper> findAllList();





}
