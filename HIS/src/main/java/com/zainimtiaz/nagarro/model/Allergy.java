package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zainimtiaz.nagarro.wrapper.AllergyWrapper;

import javax.persistence.*;
import java.io.Serializable;

/*
 * @author    : Tahir Mehmood
 * @Date      : 31-Jul-2018
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
 * @FileName  : User
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "ALLERGY")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Allergy extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ALERGY_TYPE")
    private String allergyType;

    @Column(name = "REACTION")
    private String reaction;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "NOTE")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPOINTMENT_ID")
    private Appointment appointment;*/


    public Allergy() {
    }

    public Allergy(AllergyWrapper allergyWrapper) {
        this.name = allergyWrapper.getName();
        this.allergyType = allergyWrapper.getAllergyType();
        this.reaction = allergyWrapper.getReaction();
        this.status = allergyWrapper.getStatus();
        this.note = allergyWrapper.getNote();
    }

    public Allergy(Allergy allergy, AllergyWrapper allergyWrapper) {
        allergy.name = allergyWrapper.getName();
        allergy.allergyType = allergyWrapper.getAllergyType();
        allergy.reaction = allergyWrapper.getReaction();
        allergy.status = allergyWrapper.getStatus();
        allergy.note = allergyWrapper.getNote();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAllergyType() {
        return allergyType;
    }

    public void setAlergyType(String allergyType) {
        this.allergyType = allergyType;
    }

    public void setAllergyType(String allergyType) {
        this.allergyType = allergyType;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

   /* public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }*/
}
