package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;




@Entity
@Table(name = "PATIENT_IMAGE_SETUP")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientImageSetup  extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "Name")
    private String name;

    @Column(name = "Code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "Description")
    private String description;

    @Column(name = "STATUS")
    private boolean status;

    public PatientImageSetup() {
    }


    public PatientImageSetup(PatientImageSetup patientImageSetup) {
        this.setId(patientImageSetup.getId());
        this.name = patientImageSetup.getName();
        this.code = patientImageSetup.getCode();
        this.description = patientImageSetup.getDescription();
        this.status = patientImageSetup.getStatus();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
