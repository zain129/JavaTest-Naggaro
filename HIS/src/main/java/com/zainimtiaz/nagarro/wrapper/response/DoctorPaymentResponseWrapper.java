package com.zainimtiaz.nagarro.wrapper.response;

public class DoctorPaymentResponseWrapper {


    Long pId;
    String firstName;
    String lastName;
    Double advanceBalance;

    public DoctorPaymentResponseWrapper() {}

    public DoctorPaymentResponseWrapper(Long pId, String firstName, String lastName, Double advanceBalance) {
        this.pId = pId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.advanceBalance = advanceBalance;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getAdvanceBalance() {
        return advanceBalance;
    }

    public void setAdvanceBalance(Double advanceBalance) {
        this.advanceBalance = advanceBalance;
    }
}
