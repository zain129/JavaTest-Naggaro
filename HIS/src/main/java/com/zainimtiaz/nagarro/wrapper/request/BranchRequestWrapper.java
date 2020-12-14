package com.zainimtiaz.nagarro.wrapper.request;

import com.zainimtiaz.nagarro.wrapper.ExamRooms;

/*
 * @author    Waqas Kamran
 * @Date      ;18
 * @version   ;0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project  HIS
 * @FileName  BranchRequestWrapper
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class BranchRequestWrapper {
    long id;
    String name;
    String country;
    String state;
    String city;
    Long rooms;
    String username;
    String firstName;
    String lastName;
    Long doctorId;
    boolean checkedBranch;
    long value;
    String label;
    String companyName;

    String branchName;
    String officeHoursStart;
    String officeHoursEnd;
    Long noOfExamRooms;
    String stateId;
    String cityId;
    Long primaryDoctor;
    String zipCode;
    String address;
    String officePhone;
    String fax;
    String formattedAddress;
    ExamRooms[] examRooms;
    String countryId;
    String billingName;
    String billingBranch;
    String billingTaxID;
    boolean showBranchOnline;
    boolean allowOnlineSchedulingInBranch;
    String flow;


    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingBranch() {
        return billingBranch;
    }

    public void setBillingBranch(String billingBranch) {
        this.billingBranch = billingBranch;
    }

    public String getBillingTaxID() {
        return billingTaxID;
    }

    public void setBillingTaxID(String billingTaxID) {
        this.billingTaxID = billingTaxID;
    }

    public boolean isShowBranchOnline() {
        return showBranchOnline;
    }

    public void setShowBranchOnline(boolean showBranchOnline) {
        this.showBranchOnline = showBranchOnline;
    }

    public boolean isAllowOnlineSchedulingInBranch() {
        return allowOnlineSchedulingInBranch;
    }

    public void setAllowOnlineSchedulingInBranch(boolean allowOnlineSchedulingInBranch) {
        this.allowOnlineSchedulingInBranch = allowOnlineSchedulingInBranch;
    }

    public BranchRequestWrapper() {
    }

    public ExamRooms[] getExamRooms() {
        return examRooms;
    }

    public void setExamRooms(ExamRooms[] examRooms) {
        this.examRooms = examRooms;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getOfficeHoursStart() {
        return officeHoursStart;
    }

    public void setOfficeHoursStart(String officeHoursStart) {
        this.officeHoursStart = officeHoursStart;
    }

    public String getOfficeHoursEnd() {
        return officeHoursEnd;
    }

    public void setOfficeHoursEnd(String officeHoursEnd) {
        this.officeHoursEnd = officeHoursEnd;
    }

    public Long getNoOfExamRooms() {
        return noOfExamRooms;
    }

    public void setNoOfExamRooms(Long noOfExamRooms) {
        this.noOfExamRooms = noOfExamRooms;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setPrimaryDoctor(Long primaryDoctor) {
        this.primaryDoctor = primaryDoctor;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public long getPrimaryDoctor() {
        return primaryDoctor;
    }

    public void setPrimaryDoctor(long primaryDoctor) {
        this.primaryDoctor = primaryDoctor;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}