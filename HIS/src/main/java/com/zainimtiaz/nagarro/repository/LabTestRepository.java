package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.LabOrder;
import com.zainimtiaz.nagarro.model.LabTest;
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
public interface LabTestRepository extends JpaRepository<LabTest, Long> {

    List<LabTest> findAllByLoincCodeIn(List<String> ids);
    List<LabTest> findAllByLabOrderIn(List<LabOrder> ids);

    @Query("SELECT po " +
            "FROM com.sd.his.model.LabTest po where po.labOrder.id=:id")
    List<LabTest> findAllByLabOrder(@Param("id") long lbId);
}

