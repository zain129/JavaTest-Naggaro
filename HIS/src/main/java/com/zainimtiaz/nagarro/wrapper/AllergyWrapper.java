package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.Allergy;
import com.zainimtiaz.nagarro.utill.DateTimeUtil;
import com.zainimtiaz.nagarro.utill.HISConstants;

/**
 * Created by jamal on 8/20/2018.
 */
public class AllergyWrapper extends BaseWrapper {

    private String name;
    private String allergyType = "-1";
    private String reaction;
    private String status = "ACTIVE";
    private String note;
    private long patientId;
    private long appointmentId = -1;

    public AllergyWrapper() {
    }

    public AllergyWrapper(Allergy allergy) {
        if (allergy.getPatient() != null) {
            this.patientId = allergy.getPatient().getId();
        }
       /* if (allergy.getAppointment() != null){
            this.appointmentId = allergy.getAppointment().getId();
        }*/
        this.setId(allergy.getId());
        this.name = allergy.getName();
        this.allergyType = allergy.getAllergyType();
        this.reaction = allergy.getReaction();
        this.status = allergy.getStatus();
        this.note = allergy.getNote();
        this.setCreatedOn(DateTimeUtil.getFormattedDateFromDate(allergy.getCreatedOn(), HISConstants.DATE_FORMATE_TWO));
        this.setUpdatedOn(DateTimeUtil.getFormattedDateFromDate(allergy.getUpdatedOn(), HISConstants.DATE_FORMATE_TWO));
    }

    public AllergyWrapper(String name, String allergyType, String reaction, String status, String note) {
        this.name = name;
        this.allergyType = allergyType;
        this.reaction = reaction;
        this.status = status;
        this.note = note;
    }

    public AllergyWrapper(Long id, String createdOn, String updatedOn, String name, String allergyType, String reaction, String status, String note) {
        super(id, createdOn, updatedOn);
        this.name = name;
        this.allergyType = allergyType;
        this.reaction = reaction;
        this.status = status;
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllergyType() {
        return allergyType;
    }

    public void setAllergyType(String allergyType) {
        this.allergyType = allergyType;
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

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

   /* public long getAppointmentId() {
        return appointmentId;
    }
    */

  /* public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }*/
}
