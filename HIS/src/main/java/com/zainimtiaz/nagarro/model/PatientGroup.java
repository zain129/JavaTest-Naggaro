package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.zainimtiaz.nagarro.wrapper.PatientGroupWrapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by jamal on 10/23/2018.
 */
@Entity
@Table(name = "patient_group")
public class PatientGroup extends BaseEntity {

    @Column(name = "NAME", length = 255)
    private String name;

    @Column(name = "DESCRIPTION", length = 4000)
    private String description;

    @Column(name = "STATUS")
    private boolean status;

    @OneToMany(targetEntity = Patient.class, mappedBy = "patientGroup")
    @JsonBackReference
    private List<Patient> patients;

    public PatientGroup() {
    }

    public PatientGroup(PatientGroupWrapper patientGroupWrapper) {
        this.name = patientGroupWrapper.getName();
        this.description = patientGroupWrapper.getDescription();
        this.status = patientGroupWrapper.isStatus();
    }

    public PatientGroup(PatientGroup patientGroup, PatientGroupWrapper patientGroupWrapper) {
        patientGroup.name = patientGroupWrapper.getName();
        patientGroup.description = patientGroupWrapper.getDescription();
        patientGroup.status = patientGroupWrapper.isStatus();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
