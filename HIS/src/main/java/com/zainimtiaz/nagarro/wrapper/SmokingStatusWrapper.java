package com.zainimtiaz.nagarro.wrapper;

public class SmokingStatusWrapper {
    private Long patientId;
    private Long smokingId;
    ////////////Smoking Status
    private String smokingStatus;
    private String startDate;
    private String endDate;
    private String recordedDate;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getSmokingId() {
        return smokingId;
    }

    public void setSmokingId(Long smokingId) {
        this.smokingId = smokingId;
    }

    public String getSmokingStatus() {
        return smokingStatus;
    }

    public void setSmokingStatus(String smokingStatus) {
        this.smokingStatus = smokingStatus;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(String recordedDate) {
        this.recordedDate = recordedDate;
    }
}
