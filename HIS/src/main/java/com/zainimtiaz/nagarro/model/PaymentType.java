package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PAYMENT_TYPE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @ManyToOne
    @JoinColumn(name = "gl_account")
    private GeneralLedger paymentGlAccount;


    @Column(name = "is_active", columnDefinition = "boolean default true", nullable = false)
    private Boolean active;

    @Column(name = "payment_title")
    private  String paymentTitle;

    @Column(name = "payment_mode")
    private  String paymentMode;

    @Column(name = "payment_purpose")
    private  String paymentPurpose;

    @Column(name="payment_servicecharges", columnDefinition = "double default '0.00'")
    private Double serviceCharges;

    @JsonIgnore
    @OneToMany(mappedBy = "paymentType")
    private List<PatientRefund> patientRefunds;


    @JsonIgnore
    @OneToMany(mappedBy = "paymentType")
    private List<StaffPayment> staffPayment;
    @JsonIgnore
    @OneToMany (mappedBy="paymentType")
    private List<ReceiptPaymentType> ReceiptPaymentType;


    public Double getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(Double serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public Double getMaxCardCharges() {
        return maxCardCharges;
    }

    public void setMaxCardCharges(Double maxCardCharges) {
        this.maxCardCharges = maxCardCharges;
    }

    @Column(name="payment_maxCardCharges")
    private Double maxCardCharges;

    @Column(name="payment_payCredit")
    private String payCredit;

    /*@Column(name="payment_bankGlCharges")
    private String bankGlCharges;*/

    public Boolean getPatient() {
        return isPatient;
    }

    public void setPatient(Boolean patient) {
        isPatient = patient;
    }

    @Column(name = "is_patient")
    private boolean isPatient;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "payment_bankGlCharges")
    private GeneralLedger bankGlCharges;

    public String getPaymentMode() {
        return paymentMode;
    }


    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }



    public String getPayCredit() {
        return payCredit;
    }

    public void setPayCredit(String payCredit) {
        this.payCredit = payCredit;
    }

 /*   public String getBankGlCharges() {
        return bankGlCharges;
    }

    public void setBankGlCharges(String bankGlCharges) {
        this.bankGlCharges = bankGlCharges;
    }
*/


    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPaymentTitle() {
        return paymentTitle;
    }

    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public GeneralLedger getPaymentGlAccount() {
        return paymentGlAccount;
    }

    public void setPaymentGlAccount(GeneralLedger paymentGlAccount) {
        this.paymentGlAccount = paymentGlAccount;
    }
    public GeneralLedger getBankGlCharges() {
        return bankGlCharges;
    }

    public void setBankGlCharges(GeneralLedger bankGlCharges) {
        this.bankGlCharges = bankGlCharges;
    }

    public List<PatientRefund> getPatientRefunds() {
        return patientRefunds;
    }

    public void setPatientRefunds(List<PatientRefund> patientRefunds) {
        this.patientRefunds = patientRefunds;
    }

    public List<StaffPayment> getStaffPayment() {
        return staffPayment;
    }

    public void setStaffPayment(List<StaffPayment> staffPayment) {
        this.staffPayment = staffPayment;
    }

    public List<ReceiptPaymentType> getReceiptPaymentType() {
        return ReceiptPaymentType;
    }

    public void setReceiptPaymentType(List<ReceiptPaymentType> receiptPaymentType) {
        ReceiptPaymentType = receiptPaymentType;
    }
}
