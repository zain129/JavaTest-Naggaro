package com.zainimtiaz.nagarro.wrapper;

import java.util.Date;
import java.util.List;

public class LabOrderUpdateWrapper {

    private String appointmentId;
    private String comments;
    private String doctorName;
    private String doctorSignOff;
    private List<LabTest> labTest;
    private String orderStatus;
    private Date orderTestDate;
    private long patientId;
    private Date testDate;
  //  private LabTest testNameId;
    private String testNameId;


    private byte[] image;




    public LabOrderUpdateWrapper() {

    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSignOff() {
        return doctorSignOff;
    }

    public void setDoctorSignOff(String doctorSignOff) {
        this.doctorSignOff = doctorSignOff;
    }

    public List<LabTest> getLabTest() {
        return labTest;
    }

    public void setLabTest(List<LabTest> labTest) {
        this.labTest = labTest;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderTestDate() {
        return orderTestDate;
    }

    public void setOrderTestDate(Date orderTestDate) {
        this.orderTestDate = orderTestDate;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    /*public LabTest getTestNameId() {
        return testNameId;
    }

    public void setTestNameId(LabTest testNameId) {
        this.testNameId = testNameId;
    }*/

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}
