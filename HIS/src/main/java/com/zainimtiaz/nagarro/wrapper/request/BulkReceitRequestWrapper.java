package com.zainimtiaz.nagarro.wrapper.request;

import com.zainimtiaz.nagarro.wrapper.response.InvoiceResponseWrapper;

import java.util.List;

public class BulkReceitRequestWrapper {

    private long patientId;
    private long paymentTypeId;
    private String date;

    private double paymentAmount;
    private double useAdvanceTotal;
    private boolean useAdvance;

    List<InvoiceResponseWrapper> invoiceListPaymentRequest;


    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public boolean isUseAdvance() {
        return useAdvance;
    }

    public void setUseAdvance(boolean useAdvance) {
        this.useAdvance = useAdvance;
    }

    public List<InvoiceResponseWrapper> getInvoiceListPaymentRequest() {
        return invoiceListPaymentRequest;
    }

    public void setInvoiceListPaymentRequest(List<InvoiceResponseWrapper> invoiceListPaymentRequest) {
        this.invoiceListPaymentRequest = invoiceListPaymentRequest;
    }

    public double getUseAdvanceTotal() {
        return useAdvanceTotal;
    }

    public void setUseAdvanceTotal(double useAdvanceTotal) {
        this.useAdvanceTotal = useAdvanceTotal;
    }
}
