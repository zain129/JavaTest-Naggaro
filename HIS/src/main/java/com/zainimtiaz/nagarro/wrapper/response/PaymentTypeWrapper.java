package com.zainimtiaz.nagarro.wrapper.response;

import com.sd.his.model.*;
import com.zainimtiaz.nagarro.model.GeneralLedger;
import com.zainimtiaz.nagarro.model.PaymentType;

public class PaymentTypeWrapper {

    private GeneralLedger paymentGlAccount;
    private String payCredit;
    private long id;
    private String paymentTitle;
    private String paymentMode;
    private  String paymentPurpose;
    private double serviceCharges;
    private double maxCardCharges;
    private boolean active;
    private String paymentGlAccountName;
    private long paymentGlAccountId;


    private String strServiceCharges;
    private String strmaxCardCharges;
    public boolean isPatient() {
        return isPatient;
    }

    private boolean isPatient;
    private GeneralLedger bankGlCharges;
    private boolean hasChild;

    public PaymentTypeWrapper() {

    }

    public PaymentTypeWrapper(PaymentType p) {
        this.id = p.getId();
        this.paymentTitle = p.getPaymentTitle();
        this.paymentMode = p.getPaymentMode();
        this.serviceCharges = p.getServiceCharges() == null ? 0D : p.getServiceCharges();
        this.maxCardCharges = p.getMaxCardCharges() == null ? 0D : p.getMaxCardCharges();
        this.active = p.getActive();

        if (p.getPaymentGlAccount() != null) {
            this.paymentGlAccountId = p.getPaymentGlAccount().getId();
            this.paymentGlAccountName = p.getPaymentGlAccount().getName();
        }

        this.active=p.getActive();
        this.bankGlCharges=p.getBankGlCharges();
        this.payCredit=p.getPayCredit();
        this.paymentGlAccount=p.getPaymentGlAccount();
        this.paymentMode=p.getPaymentMode();
        this.paymentPurpose=p.getPaymentPurpose();
        this.paymentTitle=p.getPaymentTitle();
        this.hasChild = (p.getPatientRefunds() != null && p.getPatientRefunds().size() > 0)
                || (p.getStaffPayment() != null && p.getStaffPayment().size() > 0)
                || (p.getReceiptPaymentType() != null && p.getReceiptPaymentType().size() > 0);
    }

    public void setServiceCharges(Double serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public void setMaxCardCharges(Double maxCardCharges) {
        this.maxCardCharges = maxCardCharges;
    }



    public void setIsPatient(boolean patient) {
        isPatient = patient;
    }

    public String getPayCredit() {
        return payCredit;
    }

    public void setPayCredit(String payCredit) {
        this.payCredit = payCredit;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPaymentTitle() {
        return paymentTitle;
    }

    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public double getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(double serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public double getMaxCardCharges() {
        return maxCardCharges;
    }

    public void setMaxCardCharges(double maxCardCharges) {
        this.maxCardCharges = maxCardCharges;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPaymentGlAccountName() {
        return paymentGlAccountName;
    }

    public void setPaymentGlAccountName(String paymentGlAccountName) {
        this.paymentGlAccountName = paymentGlAccountName;
    }

    public long getPaymentGlAccountId() {
        return paymentGlAccountId;
    }

    public void setPaymentGlAccountId(long paymentGlAccountId) {
        this.paymentGlAccountId = paymentGlAccountId;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public String getStrServiceCharges() {
        return strServiceCharges;
    }

    public void setStrServiceCharges(String strServiceCharges) {
        this.strServiceCharges = strServiceCharges;
    }

    public String getStrmaxCardCharges() {
        return strmaxCardCharges;
    }

    public void setStrmaxCardCharges(String strmaxCardCharges) {
        this.strmaxCardCharges = strmaxCardCharges;
    }

}
