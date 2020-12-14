package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.PatientGroup;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;

import java.util.Date;

/**
 * Created by jamal on 10/23/2018.
 */
public class PatientGroupWrapper extends BaseWrapper {
    private String name;
    private String description;
    private boolean status;
    private boolean hasChild;
    private long patientCount;

    public PatientGroupWrapper() {
    }

    public PatientGroupWrapper(Long id, Date createdOn, Date updatedOn, String name, String description, boolean status, boolean hasChild) {
        super(id,
                HISCoreUtil.convertDateToString(createdOn),
                HISCoreUtil.convertDateToString(updatedOn));
        this.name = name;
        this.description = description;
        this.status = status;
        this.hasChild = hasChild;
    }

    public PatientGroupWrapper(PatientGroup patientGroup) {
        super(patientGroup.getId(),
                HISCoreUtil.convertDateToString(patientGroup.getCreatedOn()),
                HISCoreUtil.convertDateToString(patientGroup.getUpdatedOn()));
        this.name = patientGroup.getName();
        this.description = patientGroup.getDescription();
        this.status = patientGroup.isStatus();
        this.patientCount = patientGroup.getPatients().size();
        this.hasChild = this.patientCount > 0;      //if(size>0) then true else false;
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

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public long getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(long patientCount) {
        this.patientCount = patientCount;
    }
}
