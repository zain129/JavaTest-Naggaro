package com.zainimtiaz.nagarro.wrapper.reports;

import java.util.Date;

public class PatientPaymentReportWrapper {

    private String paymentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String patientEMR;
    private Date paymentDate;
    private String invoiceId;
    private String paymentMethod;
    private Double invoiceAmount;
    private Double paidAmount;
    private Double discountAmount;
//    private Double refund;
    private Double advance;
    private Double appliedAmount;
    private Double balance;

    public PatientPaymentReportWrapper() {
    }

    public PatientPaymentReportWrapper(String paymentId, String firstName, String middleName, String lastName, String patientEMR,
                                       Date paymentDate, String invoiceId, Double invoiceAmount,
                                       Double paidAmount, Double discountAmount, Double advance) {
        this.paymentId = paymentId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.patientEMR = patientEMR;
        this.paymentDate = paymentDate;
        this.invoiceId = invoiceId;
        this.invoiceAmount = invoiceAmount == null ? 0D : invoiceAmount;
        this.paidAmount = paidAmount == null ? 0D : paidAmount;
        this.discountAmount = discountAmount == null ? 0D : discountAmount;
        this.advance = advance == null ? 0D : advance;
        this.appliedAmount = this.invoiceAmount - this.discountAmount - this.advance;
        this.balance = this.appliedAmount - this.paidAmount;
        this.fullName = this.firstName + ((this.middleName == null || this.middleName.trim().equals("")) ? " " : " " + this.middleName + " ") + this.lastName;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

//    public Double getRefund() {
//        return refund;
//    }

//    public void setRefund(Double refund) {
//        this.refund = refund;
//    }

    public Double getAdvance() {
        return advance;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public Double getAppliedAmount() {
        return appliedAmount;
    }

    public void setAppliedAmount(Double appliedAmount) {
        this.appliedAmount = appliedAmount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPatientEMR() {
        return patientEMR;
    }

    public void setPatientEMR(String patientEMR) {
        this.patientEMR = patientEMR;
    }
}
