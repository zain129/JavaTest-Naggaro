package com.zainimtiaz.nagarro.wrapper.request;

import com.zainimtiaz.nagarro.model.Patient_Order;
import com.zainimtiaz.nagarro.repository.PatientOrderRepository;
import com.zainimtiaz.nagarro.wrapper.Patient_OrderWrapper;
import org.springframework.beans.factory.annotation.Autowired;

public class Patient_OrderWrapper_Update {

    private long patientId;
    private String order;
    private String type;
    private String description;
    private String doctorComment;
    private long orderId;
    private String status;
    @Autowired
    private PatientOrderRepository patientOrderRepository;

    public Patient_OrderWrapper_Update(){}

    public Patient_OrderWrapper_Update(Patient_Order patientOrder, Patient_OrderWrapper_Update patientWrapper) {
        Patient_OrderWrapper objWrapper = this.patientOrderRepository.findOrderById(patientWrapper.getOrderId());
        patientOrder.setOrder(objWrapper.getOrderObj());
        patientOrder.setStatus(patientWrapper.getStatus());
        patientOrder.setDescription(patientWrapper.getDescription());
        patientOrder.setDoctorComment(patientWrapper.getDoctorComment());
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
