package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.Appointment;
import com.zainimtiaz.nagarro.model.Patient;

import java.util.Date;
import java.util.List;

public class LabOrderWrapper {



    //create wrapper
    private long id;
    private String orderStatus;
    private String doctorSignOff;
    private String comments;
    private LabTest[] labTest;
    private List<LabTest> labTestVales;
    private String orderTestDate;
    private Long patientId;
    private Long appointmentId;
    private Patient patient;



    private String strAppDate;


    private String strDate;


    // New Values Added
    String idNew;
    String descriptionNew ;
    String resultValue;
    String units;
    String normalRange;
    String loincCode;

    public String getIdNew() {
        return idNew;
    }

    public void setIdNew(String idNew) {
        this.idNew = idNew;
    }

    public String getDescriptionNew() {
        return descriptionNew;
    }

    public void setDescriptionNew(String descriptionNew) {
        this.descriptionNew = descriptionNew;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(String normalRange) {
        this.normalRange = normalRange;
    }

    public String getLoincCode() {
        return loincCode;
    }

    public void setLoincCode(String loincCode) {
        this.loincCode = loincCode;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getMaxNormalRange() {
        return maxNormalRange;
    }

    public void setMaxNormalRange(String maxNormalRange) {
        this.maxNormalRange = maxNormalRange;
    }

    public String getMinNormalRange() {
        return minNormalRange;
    }

    public void setMinNormalRange(String minNormalRange) {
        this.minNormalRange = minNormalRange;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    String testCode;
    String testName;
    String maxNormalRange;
    String minNormalRange;
    String unit;

    private  Appointment appointment;

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    private Date testDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LabOrderWrapper() {
    }

    public LabOrderWrapper(String orderStatus, String comments, Date orderTestDate, Patient patientId, Appointment appointmentId) {
        this.orderStatus = orderStatus;
     //   this.doctorSignOff = doctorSignOff;
        this.comments = comments;
       // this.labTestVales = labTest;
     //   this.orderTestDate = orderTestDate;
        this.patient = patientId;
        this.appointment = appointmentId;
       this.testDate = orderTestDate;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LabTest[] getLabTest() {
        return labTest;
    }

    public void setLabTest(LabTest[] labTest) {
        this.labTest = labTest;
    }

    public String getOrderTestDate() {
        return orderTestDate;
    }

    public void setOrderTestDate(String orderTestDate) {
        this.orderTestDate = orderTestDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDoctorSignOff() {
        return doctorSignOff;
    }

    public void setDoctorSignOff(String doctorSignOff) {
        this.doctorSignOff = doctorSignOff;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }



    public void setLabTest(List<LabTest> labTest) {
        this.labTestVales = labTest;
    }



    public List<LabTest> getLabTestVales() {
        return labTestVales;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrAppDate() {
        return strAppDate;
    }

    public void setStrAppDate(String strAppDate) {
        this.strAppDate = strAppDate;
    }
}

