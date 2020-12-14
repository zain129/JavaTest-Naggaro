package com.zainimtiaz.nagarro.wrapper.response;

import com.zainimtiaz.nagarro.model.InvoiceItems;

public class InvoiceItemsResponseWrapper {

    long id;
    String code;
    String serviceName;
    String description;
    Integer quantity;
    Double unitFee;
    Double taxRate;
    Double discountRate;
    long appointmentId;


    public InvoiceItemsResponseWrapper(InvoiceItems invoiceItems){
        this.id = invoiceItems.getId();
        this.code = invoiceItems.getCode();
        this.serviceName = invoiceItems.getServiceName();
        this.description = invoiceItems.getDescription();
        this.quantity = invoiceItems.getQuantity();
        this.unitFee = invoiceItems.getUnitFee();
        this.taxRate = invoiceItems.getTaxRate();
        this.discountRate = invoiceItems.getDiscountRate();
        this.appointmentId = invoiceItems.getInvoice().getAppointment().getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitFee() {
        return unitFee;
    }

    public void setUnitFee(Double unitFee) {
        this.unitFee = unitFee;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }


    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }
}
