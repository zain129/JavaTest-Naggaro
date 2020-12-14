package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.Branch;
import com.zainimtiaz.nagarro.model.Doctor;
import com.zainimtiaz.nagarro.model.Room;
import com.zainimtiaz.nagarro.model.User;

import java.util.List;

/*
 * @author    : Qari Muhammad Jamal
 * @Date      : 17-Apr-18
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
 * @Package   : com.sd.his.*
 * @FileName  : BranchWrapper
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class BranchWrapper {

    private String billingBranchName;
    private String billingTaxId;
    private int noOfRooms;
    private boolean primaryBranch;
    private  boolean primaryDr;
    private boolean checkedBranch;
    private  long id;
    private String branchName;
    private String name;
    private  String officeHoursStart;
    private  String officeHoursEnd;
    private  Integer noOfExamRooms;
    private  String state;
    private  String city;
    private  String primaryDoctor;
    private  Integer zipCode;
    private  String country;
    private  String officePhone;
    private  String fax;
    private  String formattedAddress;
    private  String billingName;
    private  String billingBranch;
    private  String billingTaxID;
    private Boolean showBranchOnline;
    private  Boolean allowOnlineSchedulingInBranch;
    private  Long rooms;
    private  String username;
    private   List<Room> examRooms;
    private  String address;
    private  User user;
    private  Room roomList;
    private  Doctor doctor;
    private  String firstName;
    private  String lastName;

    public BranchWrapper() {
    }

    /*public BranchWrapper(BranchUser branchUser) {
        this.name = branchUser.getBranch().getName();
        this.billingBranchName = branchUser.getBranch().getBillingBranchName();
        this.billingTaxId = branchUser.getBranch().getBillingTaxId();
        this.noOfRooms = branchUser.getBranch().getNoOfRooms();
        this.primaryBranch = branchUser.isPrimaryBranch();
        this.primaryDr = branchUser.isPrimaryDr();
        this.billingBranch = branchUser.isBillingBranch();
    }*/

    public BranchWrapper(Branch branch) {
        this.id = branch.getId();
        this.name = branch.getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBillingBranchName() {
        return billingBranchName;
    }

    public void setBillingBranchName(String billingBranchName) {
        this.billingBranchName = billingBranchName;
    }

    public String getBillingTaxId() {
        return billingTaxId;
    }

    public void setBillingTaxId(String billingTaxId) {
        this.billingTaxId = billingTaxId;
    }

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public boolean isPrimaryBranch() {
        return primaryBranch;
    }

    public void setPrimaryBranch(boolean primaryBranch) {
        this.primaryBranch = primaryBranch;
    }

    public boolean isPrimaryDr() {
        return primaryDr;
    }

    public void setPrimaryDr(boolean primaryDr) {
        this.primaryDr = primaryDr;
    }

    public boolean isCheckedBranch() {
        return checkedBranch;
    }

    public void setCheckedBranch(boolean checkedBranch) {
        this.checkedBranch = checkedBranch;
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

    public Integer getNoOfExamRooms() {
        return noOfExamRooms;
    }

    public void setNoOfExamRooms(Integer noOfExamRooms) {
        this.noOfExamRooms = noOfExamRooms;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrimaryDoctor() {
        return primaryDoctor;
    }

    public void setPrimaryDoctor(String primaryDoctor) {
        this.primaryDoctor = primaryDoctor;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public Boolean getShowBranchOnline() {
        return showBranchOnline;
    }

    public void setShowBranchOnline(Boolean showBranchOnline) {
        this.showBranchOnline = showBranchOnline;
    }

    public Boolean getAllowOnlineSchedulingInBranch() {
        return allowOnlineSchedulingInBranch;
    }

    public void setAllowOnlineSchedulingInBranch(Boolean allowOnlineSchedulingInBranch) {
        this.allowOnlineSchedulingInBranch = allowOnlineSchedulingInBranch;
    }

    public Long getRooms() {
        return rooms;
    }

    public void setRooms(Long rooms) {
        this.rooms = rooms;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Room> getExamRooms() {
        return examRooms;
    }

    public void setExamRooms(List<Room> examRooms) {
        this.examRooms = examRooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoomList() {
        return roomList;
    }

    public void setRoomList(Room roomList) {
        this.roomList = roomList;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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
}