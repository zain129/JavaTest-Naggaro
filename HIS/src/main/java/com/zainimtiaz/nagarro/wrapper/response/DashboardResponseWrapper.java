package com.zainimtiaz.nagarro.wrapper.response;

import com.zainimtiaz.nagarro.utill.HISCoreUtil;

import java.util.Date;

public class DashboardResponseWrapper {
    private String inTime;
    private String branch;
    private String group;
    private String scheduleAt;
    private String doctorFirstName;
    private String doctorLastName;
    private String examRoom;
    private String status;
    private String cashierName;
    private Long branchId;
    private Long doctorId;
    private Long roomId;
    private Long appointmentId;
    private String apptNaturalId;
    private String patientFirstName;
    private String patientLastName;
    private Long patientId;
    private Long statusId;
    private String hashColor;


    public DashboardResponseWrapper(Long appointmentId,String apptNaturalId,Long patientId ,String patientFirstName, String patientLastName,String statuName,String hashColor,Long statusId ,Date inTime, String doctorFirstName, String doctorLastName, String branch, String group, Date scheduleAt, String examRoom, Long branchId, Long doctorId, Long roomId) {
        this.appointmentId=appointmentId;
        this.inTime = HISCoreUtil.convertDateAndTimeToString(scheduleAt);
        this.patientFirstName =patientFirstName;
        this.patientLastName =patientLastName;
        this.appointmentId =appointmentId;
        this.branch = branch;
        this.group = group;
        this.scheduleAt = HISCoreUtil.convertDateAndTimeToString(scheduleAt);
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName =doctorLastName;
        this.examRoom = examRoom;http:
        this.status = statuName;
        this.statusId =statusId;
        this.branchId = branchId;
        this.doctorId = doctorId;
        this.roomId = roomId;
        this.apptNaturalId =apptNaturalId;
        this.patientId=patientId;


    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getApptNaturalId() {
        return apptNaturalId;
    }

    public void setApptNaturalId(String apptNaturalId) {
        this.apptNaturalId = apptNaturalId;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }



    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getScheduleAt() {
        return scheduleAt;
    }

    public void setScheduleAt(String scheduleAt) {
        this.scheduleAt = scheduleAt;
    }

    public String getExamRoom() {
        return examRoom;
    }

    public void setExamRoom(String examRoom) {
        this.examRoom = examRoom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
