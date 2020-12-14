package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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
 * @FileName  : Branch
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "BRANCH")
//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Branch extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    private String name;
//
//    @Column(name = "NO_OF_ROOMS")
//    private Long noOfRooms;

    @NaturalId
    @Column(name = "BRANCH_ID", unique = true, nullable = false, updatable = false)
    private String branchId;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "FORMATTED_ADDRESS")
    private String formattedAddress;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "OFFICE_PHONE")
    private String officePhone;

    @Column(name = "FLOW")
    private String flow; //enum

    @Temporal(TemporalType.TIME)
    @Column(name = "OFFICE_START_TIME")
    private Date officeStartTime;

    @Temporal(TemporalType.TIME)
    @Column(name = "OFFICE_END_TIME")
    private Date officeEndTime;

    @Column(name = "STATUS", columnDefinition = "boolean default true", nullable = false)
    private Boolean status;

    //STATUS boolean

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "SYSTEM_BRANCH", columnDefinition = "boolean default false", nullable = false)
    private Boolean systemBranch;

    @JsonIgnore
    @OneToMany(targetEntity = BranchDoctor.class, mappedBy = "branch")
    private List<BranchDoctor> branchDoctors;

    @JsonIgnore
    @OneToMany(targetEntity = BranchNurse.class, mappedBy = "branch")
    private List<BranchNurse> branchNurses;

    @JsonIgnore
    @OneToMany(targetEntity = BranchCashier.class, mappedBy = "branch")
    private List<BranchCashier> branchCashiers;

    @JsonIgnore
    @OneToMany(targetEntity = BranchReceptionist.class, mappedBy = "branch")
    private List<BranchReceptionist> branchReceptionists;

    @JsonIgnore
    @OneToMany(targetEntity = BranchMedicalService.class, mappedBy = "branch")
    private List<BranchMedicalService> branchMedicalServices;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Room.class, mappedBy = "branch")
    private List<Room> rooms;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    private Organization organization;

    @JsonIgnore
    @OneToMany(targetEntity = BranchDepartment.class, mappedBy = "branch")
    private List<BranchDepartment> branchDepartments;

    @JsonIgnore
    @OneToMany(mappedBy = "branch")
    private List<Appointment> appointments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CITY_ID")
    @JsonBackReference
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_ID")
    @JsonBackReference
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    @JsonBackReference
    private Country country;

    public Branch(String name, String address, String fax, String officePhone, String flow, Date officeStartTime, Date officeEndTime, Boolean status, Boolean systemBranch, Organization organization) {
        this.name = name;
        this.address = address;
        this.fax = fax;
        this.officePhone = officePhone;
        this.flow = flow;
        this.officeStartTime = officeStartTime;
        this.officeEndTime = officeEndTime;
        this.status = status;
        this.systemBranch = systemBranch;
        this.organization = organization;
    }

    public Branch() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public Date getOfficeStartTime() {
        return officeStartTime;
    }

    public void setOfficeStartTime(Date officeStartTime) {
        this.officeStartTime = officeStartTime;
    }

    public Date getOfficeEndTime() {
        return officeEndTime;
    }

    public void setOfficeEndTime(Date officeEndTime) {
        this.officeEndTime = officeEndTime;
    }

    public Boolean getSystemBranch() {
        return systemBranch;
    }

    public void setSystemBranch(Boolean systemBranch) {
        this.systemBranch = systemBranch;
    }

    public List<BranchDoctor> getBranchDoctors() {
        return branchDoctors;
    }

    public void setBranchDoctors(List<BranchDoctor> branchDoctors) {
        this.branchDoctors = branchDoctors;
    }

    public List<BranchNurse> getBranchNurses() {
        return branchNurses;
    }

    public void setBranchNurses(List<BranchNurse> branchNurses) {
        this.branchNurses = branchNurses;
    }

    public List<BranchCashier> getBranchCashiers() {
        return branchCashiers;
    }

    public void setBranchCashiers(List<BranchCashier> branchCashiers) {
        this.branchCashiers = branchCashiers;
    }

    public List<BranchReceptionist> getBranchReceptionists() {
        return branchReceptionists;
    }

    public void setBranchReceptionists(List<BranchReceptionist> branchReceptionists) {
        this.branchReceptionists = branchReceptionists;
    }

    public List<BranchMedicalService> getBranchMedicalServices() {
        return branchMedicalServices;
    }

    public void setBranchMedicalServices(List<BranchMedicalService> branchMedicalServices) {
        this.branchMedicalServices = branchMedicalServices;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<BranchDepartment> getBranchDepartments() {
        return branchDepartments;
    }

    public void setBranchDepartments(List<BranchDepartment> branchDepartments) {
        this.branchDepartments = branchDepartments;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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
}
