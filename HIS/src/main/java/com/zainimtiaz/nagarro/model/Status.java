package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/*
 * @author    : waqas kamran
 * @Date      : 24-Apr-18
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
 * @Package   : com.sd.his.model
 * @FileName  : ClinicalDepartment
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Entity
@Table(name = "Status")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ABBREVIATION")
    private String abbreviation;

    @Column(name = "STATUS")
    private boolean status;

    @Column(name = "HASHCOLOR")
    private String hashColor;

    @OneToMany(mappedBy = "status")
    public List<Appointment> appointments ;

    @Column(name = "SYSTEM_STATUS")
    private boolean systemStatus = false;


    public Status() {
    }

    public Status(String name, String abbreviation, boolean status, String hashColor) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.status = status;
        this.hashColor = hashColor;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getHashColor() {
        return hashColor;
    }

    public void setHashColor(String hashColor) {
        this.hashColor = hashColor;
    }

    public boolean isSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(boolean systemStatus) {
        this.systemStatus = systemStatus;
    }
}
