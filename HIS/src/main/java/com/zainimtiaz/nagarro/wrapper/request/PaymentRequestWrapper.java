package com.zainimtiaz.nagarro.wrapper.request;

import java.util.List;

public class PaymentRequestWrapper {


    long id;
    long invoiceId;
    Double paidAmount;
    Double invoiceAmount;
    String status;

    Boolean useAdvancedBal;
    Double  patientAdvanceDeposit;  // Patient Advanced amount/balance

    Double  usedAdvanceDeposit;  // use from advanced amount
    Double  discountAmount;
    long paymentTypeId;



    boolean completed;




    Double usedReceiveDeposit;


    List<PatientInvoiceModeWrapper> patientInvoiceModeWrapperList;
    public PaymentRequestWrapper() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getUseAdvancedBal() {
        return useAdvancedBal;
    }

    public void setUseAdvancedBal(Boolean useAdvancedBal) {
        this.useAdvancedBal = useAdvancedBal;
    }

    public Double getPatientAdvanceDeposit() {
        return patientAdvanceDeposit;
    }

    public void setPatientAdvanceDeposit(Double patientAdvanceDeposit) {
        this.patientAdvanceDeposit = patientAdvanceDeposit;
    }

    public Double getUsedAdvanceDeposit() {
        return usedAdvanceDeposit;
    }

    public void setUsedAdvanceDeposit(Double usedAdvanceDeposit) {
        this.usedAdvanceDeposit = usedAdvanceDeposit;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public List<PatientInvoiceModeWrapper> getPatientInvoiceModeWrapperList() {
        return patientInvoiceModeWrapperList;
    }

    public void setPatientInvoiceModeWrapperList(List<PatientInvoiceModeWrapper> patientInvoiceModeWrapperList) {
        this.patientInvoiceModeWrapperList = patientInvoiceModeWrapperList;
    }

    public Double getUsedReceiveDeposit() {
        return usedReceiveDeposit;
    }

    public void setUsedReceiveDeposit(Double usedReceiveDeposit) {
        this.usedReceiveDeposit = usedReceiveDeposit;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
