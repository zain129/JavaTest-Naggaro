package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zainimtiaz.nagarro.wrapper.Patient_OrderWrapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PATIENT_ORDER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient_Order extends BaseEntity implements Serializable {

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Column(name = "TYPE")
        private String type;
        @JsonIgnoreProperties(ignoreUnknown = true)
        @Column(name = "DESCRIPTION")
        private String description;

        @Column(name = "DOCTOR_COMMENT")
        private String doctorComment;

        @ElementCollection
        @Column(name = "URL")
        private List<String>  url;

        @ManyToOne
        @JoinColumn(name = "PATIENT_ID", nullable = false)
        private Patient patient;

        @ManyToOne
        @JoinColumn(name = "Order_ID", nullable = false)
        private PatientImageSetup order;


    @Temporal(TemporalType.TIMESTAMP)
      @Column(name = "DATE_ORDER")
      private Date dateOrder;


    @Column(name = "STATUS")
    private String status;

        public Patient_Order() {
        }

        public Patient_Order(Patient_OrderWrapper odrderWrapper) {
            super();
            this.order =odrderWrapper.getOrderObj();
            this.type = odrderWrapper.getType();
            this.description = odrderWrapper.getDescription();
            this.url = odrderWrapper.getUrl();
            this.doctorComment=odrderWrapper.getDoctorComment();
            this.status=odrderWrapper.getStatus();
        }

        public Patient_Order(PatientImageSetup code, String type, String description, List<String> url,String doctorComment, Patient patient) {
            this.order = code;
            this.type = type;
            this.description = description;
            this.url = url;
            this.patient = patient;
            this.doctorComment=doctorComment;
        }

       /* public Patient_Order(Patient_OrderWrapper patient_orderWrapper, Patient patient) {
            this(patient_orderWrapper.getOrderObj(), patient_orderWrapper.getType(), patient_orderWrapper.getDescription(), patient_orderWrapper.getUrl(), patient);
        }*/

        public Patient_Order(Patient_Order patientOrder, Patient_OrderWrapper patientWrapper) {
            patientOrder.setOrder(patientWrapper.getOrderObj());
            patientOrder.setType(patientWrapper.getType());
            patientOrder.setDescription(patientWrapper.getDescription());
            patientOrder.setUrl(patientWrapper.getUrl());
            patientOrder.setDoctorComment(patientWrapper.getDoctorComment());
        }





        public Patient getPatient() {
            return patient;
        }

        public void setPatient(Patient patient) {
            this.patient = patient;
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

        public PatientImageSetup getOrder() {
        return order;
        }

        public void setOrder(PatientImageSetup order) {
        this.order = order;
        }

        public List<String> getUrl() {
        return url;
        }

        public void setUrl(List<String> url) {
        this.url = url;
        }


        public String getStatus() {
        return status;
        }

        public void setStatus(String status) {
        this.status = status;
        }

        public Date getDateOrder() {
        return dateOrder;
        }

       public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
        }

}


