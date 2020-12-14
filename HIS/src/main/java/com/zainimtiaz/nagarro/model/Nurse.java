package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author    : Tahir Mehmood
 * @Date      : 18-Jul-2018
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
 * @FileName  : Nurse
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "NURSE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Nurse extends StaffProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @OneToMany(targetEntity = NurseWithDoctor.class, mappedBy = "nurse")
    private List<NurseWithDoctor> nurseWithDoctorList;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="USER_ID", unique= true)
    private User user;

    @JsonIgnore
    @OneToMany(targetEntity = BranchNurse.class, mappedBy = "nurse")
    private List<BranchNurse> branchNurses;

    @ElementCollection
    @JoinTable(name="NURSE_DOCTOR_DASHBOARD", joinColumns=@JoinColumn(name="ID"))
    @MapKeyColumn (name="DOC_ID")
    @Column(name="NAME")
    private Map<Long, String> selectedDoctorDashboard = new HashMap<Long, String>();

    @JsonIgnore
    @OneToMany(targetEntity = NurseDepartment.class, mappedBy = "nurse", cascade=CascadeType.ALL)
    private List<NurseDepartment> nurseDepartments;

    @Column(name = "MANAGE_PATIENT_RECORDS")
    private Boolean managePatientRecords;
    @Column(name = "MANAGE_PATIENT_INVOICES")
    private Boolean managePatientInvoices;
    @Column(name = "STATUS")
    private Boolean status;
    @Column(name = "HIDE_PATIENT_PH_NUMBER")
    private Boolean hidePatientPhoneNumber;

    public List<NurseWithDoctor> getNurseWithDoctorList() {
        return nurseWithDoctorList;
    }

    public void setNurseWithDoctorList(List<NurseWithDoctor> nurseWithDoctorList) {
        this.nurseWithDoctorList = nurseWithDoctorList;
    }

    public Boolean getManagePatientRecords() {
        return managePatientRecords;
    }

    public void setManagePatientRecords(Boolean managePatientRecords) {
        this.managePatientRecords = managePatientRecords;
    }

    public Boolean getManagePatientInvoices() {
        return managePatientInvoices;
    }

    public void setManagePatientInvoices(Boolean managePatientInvoices) {
        this.managePatientInvoices = managePatientInvoices;
    }

    public Boolean getHidePatientPhoneNumber() {
        return hidePatientPhoneNumber;
    }

    public void setHidePatientPhoneNumber(Boolean hidePatientPhoneNumber) {
        this.hidePatientPhoneNumber = hidePatientPhoneNumber;
    }

    public Map<Long, String> getSelectedDoctorDashboard() {
        return selectedDoctorDashboard;
    }

    public void setSelectedDoctorDashboard(Map<Long, String> selectedDoctorDashboard) {
        this.selectedDoctorDashboard = selectedDoctorDashboard;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BranchNurse> getBranchNurses() {
        return branchNurses;
    }

    public void setBranchNurses(List<BranchNurse> branchNurses) {
        this.branchNurses = branchNurses;
    }

    public List<NurseDepartment> getNurseDepartments() {
        return nurseDepartments;
    }

    public void setNurseDepartments(List<NurseDepartment> nurseDepartments) {
        this.nurseDepartments = nurseDepartments;
    }

}
