package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.Patient;
import com.zainimtiaz.nagarro.model.PatientImageSetup;
import com.zainimtiaz.nagarro.model.Patient_Order;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

import java.util.List;


public class Patient_OrderWrapper extends BaseWrapper {


    private long patientId;
    private String order;
    private String type;
    private String description;
    private String doctorComment;
    private Date createOrder;

    public String getStrUrl() {
        return strUrl;
    }

    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
    }

    private String strUrl;


    private Patient patient;


    private String strCreatedDate;





    private List<String>  url;
    private byte[] image;



    private  String patientImageId;

    private  MultipartFile[] listOfFiles;

    public MultipartFile[] getListOfFiles() {
        return listOfFiles;
    }

    public void setListOfFiles(MultipartFile[] listOfFiles) {
        this.listOfFiles = listOfFiles;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    List<String> lstOfFiles;

    public Patient_OrderWrapper(){

    }

    /*public Patient_OrderWrapper(Long id, Date createdOn, Date updatedOn, String name, String type, String description, String url, long patientId) {
        super(id,
                HISCoreUtil.convertDateToString(createdOn, HISConstants.DATE_FORMATE_YYY_MM_DD_T_HH_MM),
                HISCoreUtil.convertDateToString(updatedOn, HISConstants.DATE_FORMATE_YYY_MM_DD_T_HH_MM));
        this.patientId = patientId;
        this.order = name;
        this.type = type;
        this.description = description;
        this.url = url;
    }*/


    public Patient_OrderWrapper(Patient_Order order) {
        super(order.getId(),HISCoreUtil.convertDateToString(order.getCreatedOn(), HISConstants.DATE_FORMATE_YYY_MM_DD_T_HH_MM),
                HISCoreUtil.convertDateToString(order.getUpdatedOn(), HISConstants.DATE_FORMATE_YYY_MM_DD_T_HH_MM));
        this.patientId = order.getPatient().getId();
        this.orderObj = order.getOrder();
        this.type = order.getType();
        this.description = order.getDescription();
        this.doctorComment=order.getDoctorComment();
        this.url = order.getUrl();
        this.status=order.getStatus();
        this.strCreatedDate=HISCoreUtil.convertDateToString(order.getCreatedOn(), HISConstants.DATE_FORMAT_APP);

    }


    public Patient_OrderWrapper(long id, List<String> url) {

        this.patientId = id;
        this.url = url;

    }


    public Patient_OrderWrapper(Long id, PatientImageSetup  order, String type, String description, List<String> url, long patientId) {
       /* super(id,HISCoreUtil.convertDateToString(createdOn, HISConstants.DATE_FORMATE_YYY_MM_DD_T_HH_MM),
                HISCoreUtil.convertDateToString(updatedOn, HISConstants.DATE_FORMATE_YYY_MM_DD_T_HH_MM));*/
        this.patientId = patientId;
        this.orderObj = order;
        this.type = type;
        this.description = description;
        this.url = url;
    }

    private PatientImageSetup orderObj;

    public Patient_OrderWrapper(long patientId, String type, String description, String doctorComment,String status) {
        this.patientId = patientId;
        this.type = type;
        this.description = description;
        this.doctorComment = doctorComment;
        this.status=status;

    }


    public Patient_OrderWrapper(String description, String doctorComment,long patientId, String status,String type ) {
        this.description=description;
        this.doctorComment= doctorComment;
      //  this.file=file;
        this.patientId=patientId;
        this.status=status;
        this.type=type;

    }




    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoctorComment() {
        return doctorComment;
    }

    public void setDoctorComment(String doctorComment) {
        this.doctorComment = doctorComment;
    }



    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public PatientImageSetup getOrderObj() {
        return orderObj;
    }

    public void setOrderObj(PatientImageSetup orderObj) {
        this.orderObj = orderObj;
    }
    public List<String> getLstOfFiles() {
        return lstOfFiles;
    }

    public void setLstOfFiles(List<String> lstOfFiles) {
        this.lstOfFiles = lstOfFiles;
    }

    public String getPatientImageId() {
        return patientImageId;
    }

    public void setPatientImageId(String patientImageId) {
        this.patientImageId = patientImageId;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public String getStrCreatedDate() {
        return strCreatedDate;
    }

    public void setStrCreatedDate(String strCreatedDate) {
        this.strCreatedDate = strCreatedDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
