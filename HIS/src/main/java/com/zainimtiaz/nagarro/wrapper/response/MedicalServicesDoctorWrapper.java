package com.zainimtiaz.nagarro.wrapper.response;

public class MedicalServicesDoctorWrapper {
    private String mServiceName;
    private String mServiceDescription;
    private Long mServiceId;
    private Long doctorMedicalServiceId;
    private Long doctorId;
    private String docFirstName;
    private String docLastName;
    private Long duration;
    private String comission;
    private Long value;
    private String label;

    public MedicalServicesDoctorWrapper(Long dmsId, Long drId, String docFirstName, String docLastName, Long msId, String msName, String msDescription,Long duration ){
        this.doctorMedicalServiceId =dmsId;
        this.doctorId =drId;
        this.docFirstName =docFirstName;
        this.docLastName =docLastName;
        this.mServiceId =msId;
        this.mServiceName=msName;
        this.mServiceDescription=msDescription;
        this.duration = duration;
        this.label =msName;
        this.value = msId;

    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getComission() {
        return comission;
    }

    public void setComission(String comission) {
        this.comission = comission;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getmServiceName() {
        return mServiceName;
    }

    public void setmServiceName(String mServiceName) {
        this.mServiceName = mServiceName;
    }

    public String getmServiceDescription() {
        return mServiceDescription;
    }

    public void setmServiceDescription(String mServiceDescription) {
        this.mServiceDescription = mServiceDescription;
    }

    public Long getmServiceId() {
        return mServiceId;
    }

    public void setmServiceId(Long mServiceId) {
        this.mServiceId = mServiceId;
    }

    public Long getDoctorMedicalServiceId() {
        return doctorMedicalServiceId;
    }

    public void setDoctorMedicalServiceId(Long doctorMedicalServiceId) {
        this.doctorMedicalServiceId = doctorMedicalServiceId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDocFirstName() {
        return docFirstName;
    }

    public void setDocFirstName(String docFirstName) {
        this.docFirstName = docFirstName;
    }

    public String getDocLastName() {
        return docLastName;
    }

    public void setDocLastName(String docLastName) {
        this.docLastName = docLastName;
    }
}
