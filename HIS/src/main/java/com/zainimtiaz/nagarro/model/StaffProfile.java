package com.zainimtiaz.nagarro.model;


import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class StaffProfile extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @NaturalId
    @Column(name = "PROFILE_ID", unique = true, nullable = false, updatable = false)
    private String profileId;

    @Temporal(TemporalType.DATE)
    @Column(name = "DOB")
    private Date dob;

    @Column(name = "HOME_PHONE")
    private String homePhone;

    @Column(name = "CELL_PHONE")
    private String cellPhone;

    @Column(name = "OFFICE_PHONE")
    private String officePhone;

    @Column(name = "PHONE_EXTENSION")
    private String officeExtension;

    @Temporal(TemporalType.DATE)
    @Column(name = "ACCOUNT_EXPIRY")
    private Date accountExpiry;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "PROFILE_IMG_URL")
    private String profileImgURL;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "SEND_BILLING_REPORT")
    private Boolean sendBillingReport;

    @Column(name = "USE_RECEIPT_DASHBOARD")
    private Boolean useReceiptDashboard;

    @Column(name = "OTHER_DOCTOR_DASHBOARD")
    private Boolean otherDoctorDashboard;
    @Column(name = "IS_ACTIVE")
    private Boolean active;

//    @Column(name = "CITY")
//    private String city;
//
//    @Column(name = "COUNTRY")
//    private String country;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MARITAL_STATUS")
    private String maritalStatus;

    public Boolean getSendBillingReport() {
        return sendBillingReport;
    }

    public void setSendBillingReport(Boolean sendBillingReport) {
        this.sendBillingReport = sendBillingReport;
    }

    public Boolean getUseReceiptDashboard() {
        return useReceiptDashboard;
    }

    public void setUseReceiptDashboard(Boolean useReceiptDashboard) {
        this.useReceiptDashboard = useReceiptDashboard;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getOtherDoctorDashboard() {
        return otherDoctorDashboard;
    }

    public void setOtherDoctorDashboard(Boolean otherDoctorDashboard) {
        this.otherDoctorDashboard = otherDoctorDashboard;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getOfficeExtension() {
        return officeExtension;
    }

    public void setOfficeExtension(String officeExtension) {
        this.officeExtension = officeExtension;
    }

    public Date getAccountExpiry() {
        return accountExpiry;
    }

    public void setAccountExpiry(Date accountExpiry) {
        this.accountExpiry = accountExpiry;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileImgURL() {
        return profileImgURL;
    }

    public void setProfileImgURL(String profileImgURL) {
        this.profileImgURL = profileImgURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }


}