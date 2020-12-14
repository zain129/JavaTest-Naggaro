package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "STAFF_PAYMENT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StaffPayment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NaturalId
    @Column(name = "PAYMENT_ID")
    private String paymentId;

    @Column(name = "PAYMENT_ON")
    private Date paymentOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    private Doctor doctor;

    @Column(name = "PAYMENT_AMOUNT", columnDefinition = "double default '0.00'")
    private  Double paymentAmount;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_TYPE_ID", nullable = false)
    private PaymentType paymentType;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }


    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getPaymentOn() {
        return paymentOn;
    }

    public void setPaymentOn(Date paymentOn) {
        this.paymentOn = paymentOn;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
