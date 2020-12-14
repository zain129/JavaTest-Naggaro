package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.FamilyHistory;
import com.zainimtiaz.nagarro.wrapper.FamilyHistoryWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
public interface FamilyHistoryRepository extends JpaRepository<FamilyHistory, Long> {

    @Query("SELECT new com.sd.his.wrapper.FamilyHistoryWrapper(fh.id,fh.name,fh.relation,fh.status,fh.ethnicGroup,fp.id) FROM FamilyHistory fh INNER JOIN fh.patient fp WHERE fh.patient.id =?1")
    List<FamilyHistoryWrapper> findAllByPatient(Long id, Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.FamilyHistoryWrapper(fh.id,fh.name,fh.relation,fh.status,fh.ethnicGroup,fp.id) FROM FamilyHistory fh INNER JOIN fh.patient fp ")
    List<FamilyHistoryWrapper> findAllByActive(Pageable pageable);
    @Query("SELECT new com.sd.his.wrapper.FamilyHistoryWrapper(fh.id,fh.name,fh.relation,fh.status,fh.ethnicGroup,fp.id) FROM FamilyHistory fh INNER JOIN fh.patient fp ")
    List<FamilyHistoryWrapper> findAllByActive();

    /*List<LabOrderProjection> findAllProjectedBy(Pageable pageable);
     LabOrderProjection findById(Long id);
     List<LabOrder> findAllById(Long id);
     @Modifying
     Integer deleteById(long id);*/

}


