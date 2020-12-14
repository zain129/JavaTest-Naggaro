package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zainimtiaz.nagarro.enums.DutyShiftEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
 * @Package   : com.sd.his.DutyShift
 * @FileName  : DutyShift
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Entity
@Table(name = "DUTY_SHIFT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DutyShift extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Enumerated(value = EnumType.STRING) //DutyShiftEnum
    @Column(name = "SHIFT_NAME")
    private DutyShiftEnum shiftName;

    @Temporal(TemporalType.TIME)
    @Column(name = "START_TIME")
    private Date startTime;

    @Temporal(TemporalType.TIME)
    @Column(name = "END_TIME")
    private Date endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;

    @Transient
    private String startDutyTime;
    @Transient
    private String endDutyTime;
    public DutyShift(){}

    public DutyShift(DutyShiftEnum shiftName, String startDutyTime, String endDutyTime) {
        this.shiftName = shiftName;
        this.startDutyTime = startDutyTime;
        this.endDutyTime = endDutyTime;
    }

    public String getStartDutyTime() {
        return startDutyTime;
    }

    public void setStartDutyTime(String startDutyTime) {
        this.startDutyTime = startDutyTime;
    }

    public String getEndDutyTime() {
        return endDutyTime;
    }

    public void setEndDutyTime(String endDutyTime) {
        this.endDutyTime = endDutyTime;
    }

    public DutyShiftEnum getShiftName() {
        return shiftName;
    }

    public void setShiftName(DutyShiftEnum shiftName) {
        this.shiftName = shiftName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}