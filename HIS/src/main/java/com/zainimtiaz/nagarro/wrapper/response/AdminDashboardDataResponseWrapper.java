package com.zainimtiaz.nagarro.wrapper.response;

public class AdminDashboardDataResponseWrapper {

    private Long patientCount;
    private Long appointmentsCount;
    private Long medicalServicesCount;
    private Long icdsCount;

    public AdminDashboardDataResponseWrapper() {
    }

    public Long getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(Long patientCount) {
        this.patientCount = patientCount;
    }

    public Long getAppointmentsCount() {
        return appointmentsCount;
    }

    public void setAppointmentsCount(Long appointmentsCount) {
        this.appointmentsCount = appointmentsCount;
    }

    public Long getMedicalServicesCount() {
        return medicalServicesCount;
    }

    public void setMedicalServicesCount(Long medicalServicesCount) {
        this.medicalServicesCount = medicalServicesCount;
    }

    public Long getIcdsCount() {
        return icdsCount;
    }

    public void setIcdsCount(Long icdsCount) {
        this.icdsCount = icdsCount;
    }
}
