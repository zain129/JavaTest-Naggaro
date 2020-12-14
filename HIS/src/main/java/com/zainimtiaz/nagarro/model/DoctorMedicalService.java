package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DOCTOR_MEDICAL_SERVICE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorMedicalService extends BaseEntity implements Serializable{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEDICAL_SERVICE_ID")
    private MedicalService medicalService;

    @Column(name="COMMISSION_RATE", columnDefinition = "double default '0.00'")
    private Double comission;

    public DoctorMedicalService(Doctor doctor, MedicalService medicalService) {
        this.doctor = doctor;
        this.medicalService = medicalService;
    }

    public DoctorMedicalService() {
    }

    public Double getComission() {
        return comission;
    }

    public void setComission(Double comission) {
        this.comission = comission;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public MedicalService getMedicalService() {
        return medicalService;
    }

    public void setMedicalService(MedicalService medicalService) {
        this.medicalService = medicalService;
    }
}
