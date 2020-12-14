package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.enums.DutyShiftEnum;
import com.zainimtiaz.nagarro.model.Doctor;
import com.zainimtiaz.nagarro.model.DutyShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
public interface DutyShiftRepository extends JpaRepository<DutyShift, Long> {

    void deleteAllByDoctor(Doctor doctor);

    DutyShift findByDoctorAndShiftName(Doctor doctor ,DutyShiftEnum name);
    DutyShift findByDoctor(Doctor doctor);

}

