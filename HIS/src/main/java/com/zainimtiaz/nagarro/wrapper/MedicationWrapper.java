package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.Medication;
import com.zainimtiaz.nagarro.repository.AppointmentRepository;
import com.zainimtiaz.nagarro.utill.DateTimeUtil;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by jamal on 8/28/2018.
 */
public class MedicationWrapper extends BaseWrapper {

    @Autowired
    private AppointmentRepository appointmentService;

    private String drugName;
    private boolean prn;
    private String sigNote;
    private String indication;
    private String status;
    private long patientId;
    private long appointmentId = -1;

    private Date datePrescribedDate;
    private String datePrescribedString;


    private Date dateStartedTakingDate;
    private String dateStartedTakingString;


    private Date dateStoppedTakingDate;
    private String dateStoppedTakingString;


    private double dispenseQuantity;
    private String dispensePackage;
    private long numberRefill;
    private boolean daw;
    private String pharmacyNote;
    private String note;
    private String orderStatus;



    private String dteAppointment;


 //   private Appointment appointment;
    // New Values Changes
    private String frequency;
    private String route;
    private String strengths;
    private String duration;
    private String durationPeriod;
    private String appointmentDate;
    public String getAppointmentDate() {
        return appointmentDate;
    }




    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }





    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDurationPeriod() {
        return durationPeriod;
    }

    public void setDurationPeriod(String durationPeriod) {
        this.durationPeriod = durationPeriod;
    }



    public MedicationWrapper() {
    }

    public MedicationWrapper(Medication medication) throws ParseException {

        super(medication.getId(),
                HISCoreUtil.convertDateAndTimeToString(medication.getCreatedOn()),
                HISCoreUtil.convertDateAndTimeToString(medication.getUpdatedOn()));
        this.drugName = medication.getDrugName();
        this.prn = medication.isPrn();
        this.sigNote = medication.getSigNote();
        this.indication = medication.getIndication();
        this.status = medication.getStatus();
        if (medication.getPatient() != null) {
            this.patientId = medication.getPatient().getId();
        }
        if (medication.getAppointment() != null) {
            this.appointmentId = medication.getAppointment().getId();
        }

        if (medication.getDatePrescribed() != null) {
            this.datePrescribedString = DateTimeUtil.getFormattedDateFromDate(medication.getDatePrescribed(), HISConstants.DATE_FORMAT_APP);
        }
        if (medication.getDateStartedTaking() != null) {
            this.dateStartedTakingString = DateTimeUtil.getFormattedDateFromDate(medication.getDateStartedTaking(), HISConstants.DATE_FORMAT_APP);
        }
        if (medication.getDateStoppedTaking() != null) {
            this.dateStoppedTakingString = DateTimeUtil.getFormattedDateFromDate(medication.getDateStoppedTaking(), HISConstants.DATE_FORMAT_APP);
        }
        this.dispenseQuantity = medication.getDispenseQuantity();
        this.dispensePackage = medication.getDispensePackage();
        this.numberRefill = medication.getNumberRefill();
        this.daw = medication.isDaw();
        this.pharmacyNote = medication.getPharmacyNote();
        this.note = medication.getNote();
        this.orderStatus = medication.getOrderStatus();
        this.duration = medication.getDuration();
        this.frequency = medication.getFrequency();
        this.durationPeriod = medication.getDurationPeriod();
        this.route = medication.getRoute();
        this.strengths = medication.getStrengths();
    }

    public MedicationWrapper(Long id, String createdOn, String updatedOn) {
        super(id, createdOn, updatedOn);
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public boolean isPrn() {
        return prn;
    }

    public void setPrn(boolean prn) {
        this.prn = prn;
    }

    public String getSigNote() {
        return sigNote;
    }

    public void setSigNote(String sigNote) {
        this.sigNote = sigNote;
    }


    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }





    public double getDispenseQuantity() {
        return dispenseQuantity;
    }

    public void setDispenseQuantity(double dispenseQuantity) {
        this.dispenseQuantity = dispenseQuantity;
    }

    public String getDispensePackage() {
        return dispensePackage;
    }

    public void setDispensePackage(String dispensePackage) {
        this.dispensePackage = dispensePackage;
    }

    public long getNumberRefill() {
        return numberRefill;
    }

    public void setNumberRefill(long numberRefill) {
        this.numberRefill = numberRefill;
    }

    public boolean isDaw() {
        return daw;
    }

    public void setDaw(boolean daw) {
        this.daw = daw;
    }

    public String getPharmacyNote() {
        return pharmacyNote;
    }

    public void setPharmacyNote(String pharmacyNote) {
        this.pharmacyNote = pharmacyNote;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStatus() {
        return status;
    }

    public Date getDatePrescribedDate() {
        return datePrescribedDate;
    }

    public void setDatePrescribedDate(Date datePrescribedDate) {
        this.datePrescribedDate = datePrescribedDate;
    }

    public Date getDateStartedTakingDate() {
        return dateStartedTakingDate;
    }

    public void setDateStartedTakingDate(Date dateStartedTakingDate) {
        this.dateStartedTakingDate = dateStartedTakingDate;
    }

    public Date getDateStoppedTakingDate() {
        return dateStoppedTakingDate;
    }

    public void setDateStoppedTakingDate(Date dateStoppedTakingDate) {
        this.dateStoppedTakingDate = dateStoppedTakingDate;
    }

    public String getDatePrescribedString() {
        return datePrescribedString;
    }

    public void setDatePrescribedString(String datePrescribedString) {
        this.datePrescribedString = datePrescribedString;
    }

    public String getDateStartedTakingString() {
        return dateStartedTakingString;
    }

    public void setDateStartedTakingString(String dateStartedTakingString) {
        this.dateStartedTakingString = dateStartedTakingString;
    }

    public String getDateStoppedTakingString() {
        return dateStoppedTakingString;
    }

    public void setDateStoppedTakingString(String dateStoppedTakingString) {
        this.dateStoppedTakingString = dateStoppedTakingString;
    }

   /* public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }*/

    public String getDteAppointment() {
        return dteAppointment;
    }

    public void setDteAppointment(String dteAppointment) {
        this.dteAppointment = dteAppointment;
    }
}
