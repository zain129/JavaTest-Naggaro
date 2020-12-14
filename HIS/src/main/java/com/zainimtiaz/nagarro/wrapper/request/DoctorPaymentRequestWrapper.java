package com.zainimtiaz.nagarro.wrapper.request;

import com.zainimtiaz.nagarro.model.StaffPayment;

import java.util.Date;

public class DoctorPaymentRequestWrapper {
    private String paymentId;
    private String date;
    private String doctorName;
    private long doctorId;
    private long paymentTypeId;
    private String paymentTypeTitle;
    private Double amount;

    private Date createdOn;


    public DoctorPaymentRequestWrapper()
    {

    }

    public DoctorPaymentRequestWrapper(StaffPayment staffPayment)
    {
        this.paymentId = staffPayment.getPaymentId();
//        this.date = staffPayment.getCreatedOn();
        this.doctorName = staffPayment.getDoctor().getFirstName() + " " + staffPayment.getDoctor().getLastName();
        this.paymentTypeTitle = staffPayment.getPaymentType().getPaymentTitle();
        this.amount = staffPayment.getPaymentAmount();
        this.createdOn = staffPayment.getCreatedOn();
    }


    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPaymentTypeTitle() {
        return paymentTypeTitle;
    }

    public void setPaymentTypeTitle(String paymentTypeTitle) {
        this.paymentTypeTitle = paymentTypeTitle;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}