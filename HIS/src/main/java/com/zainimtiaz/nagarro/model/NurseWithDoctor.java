package com.zainimtiaz.nagarro.model;
/*
 * @author    : Tahir Mehmood
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

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DUTY_WITH_DOCTOR")
public class NurseWithDoctor extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NURSE_ID", nullable = false)
    private Nurse nurse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    private Doctor doctor;

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}