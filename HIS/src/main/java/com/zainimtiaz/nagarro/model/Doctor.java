package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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
 * @FileName  : User
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "DOCTOR")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Doctor extends StaffProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CHECK_UP_INTERVAL")
    private Long checkUpInterval;

    @ElementCollection
    @Column(name = "WORKING_DAYS")
    private List<String> workingDays;

    @ElementCollection
    @JoinTable(name="DOCTOR_DASHBOARD", joinColumns=@JoinColumn(name="ID"))
    @MapKeyColumn (name="DOC_ID")
    @Column(name="NAME")
    private Map<Long, String> selectedDoctorDashboard = new HashMap<Long, String>();

    @JsonIgnore
    @OneToMany(targetEntity = NurseWithDoctor.class, mappedBy = "doctor")
    private List<NurseWithDoctor> nurseWithDoctorList;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="USER_ID", unique= true)
    private User user;

    @ManyToOne
    @JoinColumn(name="DEPARTMENT_ID")
    private Department department;

    @JsonIgnore
    @OneToMany(targetEntity = DutyShift.class, mappedBy = "doctor" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DutyShift> dutyShifts;

    @JsonIgnore
    @OneToMany(targetEntity = BranchDoctor.class, mappedBy = "doctor")
    private List<BranchDoctor> branchDoctors;

    @JsonIgnore
    @OneToMany(mappedBy = "primaryDoctor")
    private List<Patient> patients;

    @Column(name = "VACATION", columnDefinition = "boolean default false")
    private Boolean vacation;

    @Temporal(TemporalType.DATE)
    @Column(name = "VACATION_FROM")
    private Date vacationFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "VACATION_TO")
    private Date vacationTO;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    @JsonIgnore
    @OneToMany(targetEntity = DoctorMedicalService.class, mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<DoctorMedicalService> doctorMedicalServices;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "BALANCE", columnDefinition = "double default '0.00'")
    private  Double balance;

    @Column(name = "ALLOW_DISCOUNT")
    private Double allowDiscount;
    @Column(name = "HIDE_PATIENT_PH_NUMBER")
    private Boolean hidePatientPhoneNumber;

    @Column(name = "ALLOW_DISCOUNT_CHECK")
    private Boolean allowDiscountCheck;

    @Column(name = "CAN_RECEIVE_PAYMENT")
    private Boolean canReceivePayment;

    @Column(name = "CAN_ACCESS_PATIENT")
    private Boolean canAccessPatientRecord;

    @OneToMany(mappedBy = "doctor")
    private List<StaffPayment> staffPayment;


    public Map<Long,String> getSelectedDoctorDashboard() {
        return selectedDoctorDashboard;
    }

    public void setSelectedDoctorDashboard(Map<Long, String> selectedDoctorDashboard) {
        this.selectedDoctorDashboard = selectedDoctorDashboard;
    }

    public Boolean getCanAccessPatientRecord() {
        return canAccessPatientRecord;
    }

    public Boolean getAllowDiscountCheck() {
        return allowDiscountCheck;
    }

    public void setAllowDiscountCheck(Boolean allowDiscountCheck) {
        this.allowDiscountCheck = allowDiscountCheck;
    }

    public void setCanAccessPatientRecord(Boolean canAccessPatientRecord) {
        this.canAccessPatientRecord = canAccessPatientRecord;
    }

    public Boolean getHidePatientPhoneNumber() {
        return hidePatientPhoneNumber;
    }

    public void setHidePatientPhoneNumber(Boolean hidePatientPhoneNumber) {
        this.hidePatientPhoneNumber = hidePatientPhoneNumber;
    }

    public Boolean getCanReceivePayment() {
        return canReceivePayment;
    }

    public void setCanReceivePayment(Boolean canReceivePayment) {
        this.canReceivePayment = canReceivePayment;
    }

    public Boolean getVacation() {
        return vacation;
    }

    public void setVacation(Boolean vacation) {
        this.vacation = vacation;
    }

    public Long getCheckUpInterval() {
        return checkUpInterval;
    }

    public void setCheckUpInterval(Long checkUpInterval) {
        this.checkUpInterval = checkUpInterval;
    }

    public List<String> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<String> workingDays) {
        this.workingDays = workingDays;
    }

    public List<NurseWithDoctor> getNurseWithDoctorList() {
        return nurseWithDoctorList;
    }

    public void setNurseWithDoctorList(List<NurseWithDoctor> nurseWithDoctorList) {
        this.nurseWithDoctorList = nurseWithDoctorList;
    }

    public Double getAllowDiscount() {
        return allowDiscount;
    }

    public void setAllowDiscount(Double allowDiscount) {
        this.allowDiscount = allowDiscount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DutyShift> getDutyShifts() {
        return dutyShifts;
    }

    public void setDutyShifts(List<DutyShift> dutyShifts) {
        this.dutyShifts = dutyShifts;
    }

    public List<BranchDoctor> getBranchDoctors() {
        return branchDoctors;
    }

    public Date getVacationFrom() {
        return vacationFrom;
    }

    public void setVacationFrom(Date vacationFrom) {
        this.vacationFrom = vacationFrom;
    }

    public Date getVacationTO() {
        return vacationTO;
    }

    public void setVacationTO(Date vacationTO) {
        this.vacationTO = vacationTO;
    }

    public void setBranchDoctors(List<BranchDoctor> branchDoctors) {
        this.branchDoctors = branchDoctors;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<DoctorMedicalService> getDoctorMedicalServices() {
        return doctorMedicalServices;
    }

    public void setDoctorMedicalServices(List<DoctorMedicalService> doctorMedicalServices) {
        this.doctorMedicalServices = doctorMedicalServices;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @JsonIgnore
    public List<StaffPayment> getStaffPayment() {
        return staffPayment;
    }

    public void setStaffPayment(List<StaffPayment> staffPayment) {
        this.staffPayment = staffPayment;
    }
}
