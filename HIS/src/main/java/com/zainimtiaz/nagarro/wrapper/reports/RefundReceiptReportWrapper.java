package com.zainimtiaz.nagarro.wrapper.reports;

import java.util.Date;

/**
 * Created by Zain on 12/11/2018.
 */
public class RefundReceiptReportWrapper {

    private String transId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String paymentMethod;
    private Date refundDate;
    private String patientEMR;
    private String refundType;
    private Double totalAmount;
    private Double paidAmount;

    public RefundReceiptReportWrapper() {

    }

    public RefundReceiptReportWrapper(String transId, String firstName, String middleName, String lastName, String paymentMethod,
                                      Date refundDate, String patientEMR, String refundType, Double totalAmount, Double paidAmount) {
        this.transId = transId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.paymentMethod = paymentMethod;
        this.refundDate = refundDate;
        this.patientEMR = patientEMR;
        this.refundType = refundType;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.fullName = this.firstName + ((this.middleName == null || this.middleName.trim().equals("")) ? " " : " " + this.middleName + " ") + this.lastName;
    }


    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    public String getPatientEMR() {
        return patientEMR;
    }

    public void setPatientEMR(String patientEMR) {
        this.patientEMR = patientEMR;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
