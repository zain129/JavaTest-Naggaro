package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/*
 * @author    : Tahir Mehmood
 * @Date      : 16-Jul-18
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
 * @FileName  : Organization
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "ORGANIZATION")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "DURATION_OF_EXAM")
    private Long durationOFExam;

    @Column(name = "OFFICE_PHONE")
    private String officePhone;

    @Column(name = "WEBSITE")
    private String website;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STATUS", columnDefinition = "boolean default true", nullable = false)
    private Boolean status;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "ADDRESS")
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "organization", cascade = CascadeType.PERSIST)
    private List<S3Bucket> bucketList;

    @JsonIgnore
    @OneToMany(mappedBy = "organization", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Prefix> prefixList;

    @JsonIgnore
    @OneToMany(mappedBy = "organization", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Branch> branches;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "City_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @Column(name = "HOURS_FORMAT")
    String hoursFormat;



    @Column(name = "CURRENCY_FORMAT")
    String currencyFormat;


    @Column(name = "SPECIALTY")
    private String specialty;

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    @Column(name = "date_format")
    private String dateFormat;

    @Column(name = "time_format")
    private String timeFormat;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Country_id")
    private Country country;




    @Column(name = "URL")
    private String url;

    public Organization(){}
    public Organization(String companyName, String officePhone, String website, String email, String fax, String address,Boolean status) {
        this.companyName = companyName;
        this.officePhone = officePhone;
        this.website = website;
        this.email = email;
        this.fax = fax;
        this.address = address;
        this.status = status;
        this.timeFormat=timeFormat;
        this.dateFormat=dateFormat;
        this.city=city;
        this.country=country;


   //     this.prefixList = prefixList;
   //     this.branches = branches;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getDurationOFExam() {
        return durationOFExam;
    }

    public void setDurationOFExam(Long durationOFExam) {
        this.durationOFExam = durationOFExam;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<S3Bucket> getBucketList() {
        return bucketList;
    }

    public void setBucketList(List<S3Bucket> bucketList) {
        this.bucketList = bucketList;
    }

    public List<Prefix> getPrefixList() {
        return prefixList;
    }

    public void setPrefixList(List<Prefix> prefixList) {
        this.prefixList = prefixList;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHoursFormat() {
        return hoursFormat;
    }

    public void setHoursFormat(String hoursFormat) {
        this.hoursFormat = hoursFormat;
    }

    public String getCurrencyFormat() {
        return currencyFormat;
    }

    public void setCurrencyFormat(String currencyFormat) {
        this.currencyFormat = currencyFormat;
    }

}
