package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SMOKING_STATUS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmokingStatus extends BaseEntity {
    @Column(name="status", nullable = false)
    private String smokingStatus;

    @Column(name="start_date", nullable = false)
    private Date startDate;

    @Column(name="end_date", nullable = true)
    private Date endDate;

    @Column(name="recorded_date", nullable = false)
    private Date recordedDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    private Patient patient;

    public String getSmokingStatus() {
        return smokingStatus;
    }

    public void setSmokingStatus(String smokingStatus) {
        this.smokingStatus = smokingStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(Date recordedDate) {
        this.recordedDate = recordedDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
