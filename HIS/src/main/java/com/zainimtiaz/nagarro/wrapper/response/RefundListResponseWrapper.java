package com.zainimtiaz.nagarro.wrapper.response;

import com.sd.his.model.*;
import com.zainimtiaz.nagarro.model.Invoice;
import com.zainimtiaz.nagarro.model.PatientRefund;

import java.util.Date;

public class RefundListResponseWrapper {

    private String refundId;
    private  Double refundAmount;
    private String paymentType;
    private Invoice invoice;
    private  String description;
    private  String refundType;
    private  String patientName;
    private Date date;


    public RefundListResponseWrapper() {


    }

    public RefundListResponseWrapper(PatientRefund patientRefund) {

        this.refundId = patientRefund.getRefundId();
        this.refundAmount= patientRefund.getRefundAmount();
        this.paymentType = patientRefund.getPaymentType().getPaymentTitle();
        this.description = patientRefund.getDescription();
        this.refundType = patientRefund.getRefundType();
        this.patientName = patientRefund.getPatient().getFirstName();
        this.date = patientRefund.getCreatedOn();
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
