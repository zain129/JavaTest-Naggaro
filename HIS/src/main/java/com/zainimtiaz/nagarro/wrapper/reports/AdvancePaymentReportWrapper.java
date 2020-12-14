package com.zainimtiaz.nagarro.wrapper.reports;

import java.util.Date;

public class AdvancePaymentReportWrapper {

    private String paymentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private Date paymentDate;
    private String patientEMR;
    private String description;
    private Double paidAmount;

    public AdvancePaymentReportWrapper() {
    }

    public AdvancePaymentReportWrapper(String paymentId, String firstName, String middleName, String lastName,
                                       Date paymentDate, String patientEMR, String description, Double paidAmount) {
        this.paymentId = paymentId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.paymentDate = paymentDate;
        this.patientEMR = patientEMR;
        this.description = description;
        this.paidAmount = paidAmount;
        this.fullName = this.firstName + ((this.middleName == null || this.middleName.trim().equals("")) ? " " : " " + this.middleName + " ") + this.lastName;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPatientEMR() {
        return patientEMR;
    }

    public void setPatientEMR(String patientEMR) {
        this.patientEMR = patientEMR;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

}
