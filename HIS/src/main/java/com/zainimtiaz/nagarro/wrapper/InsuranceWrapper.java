package com.zainimtiaz.nagarro.wrapper;

import java.util.Date;

/*
 * @author    : Irfan Nasim
 * @Date      : 04-Jun-18
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.model
 * @FileName  : Insurance
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */

public class InsuranceWrapper {

    private long id;
    private Date createdOn;
    private Date updatedOn;

    private String company;
    private String insuranceID;
    private String groupNumber;
    private String planName;
    private String planType;
    private Date cardIssuedDate;
    private Date cardExpiryDate;
    private String primaryInsuranceNotes;
    private String photoFrontURL;
    private String photoBackURL;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public Date getCardIssuedDate() {
        return cardIssuedDate;
    }

    public void setCardIssuedDate(Date cardIssuedDate) {
        this.cardIssuedDate = cardIssuedDate;
    }

    public Date getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(Date cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getPrimaryInsuranceNotes() {
        return primaryInsuranceNotes;
    }

    public void setPrimaryInsuranceNotes(String primaryInsuranceNotes) {
        this.primaryInsuranceNotes = primaryInsuranceNotes;
    }

    public String getPhotoFrontURL() {
        return photoFrontURL;
    }

    public void setPhotoFrontURL(String photoFrontURL) {
        this.photoFrontURL = photoFrontURL;
    }

    public String getPhotoBackURL() {
        return photoBackURL;
    }

    public void setPhotoBackURL(String photoBackURL) {
        this.photoBackURL = photoBackURL;
    }
}
