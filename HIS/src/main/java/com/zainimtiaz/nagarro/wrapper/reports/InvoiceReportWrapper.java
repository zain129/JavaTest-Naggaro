package com.zainimtiaz.nagarro.wrapper.reports;

import java.util.Date;

public class InvoiceReportWrapper {

    private String invoiceId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String paymentMode;
    private String doctorName;
    private Date schdeulledDate;
    private String serviceName;
    private Integer quantity;
    private Double serviceCharges;
    private Double discountRate;
    private Double discountAmount;
    private Double taxRate;
    private Double taxAmount;
    private Double totalAmount;

    private Double invoiceTotalDiscount;
    private Double invoiceTotalTax;
    private Double invoiceTotalAmount;

    public InvoiceReportWrapper() {
    }

    public InvoiceReportWrapper(String invoiceId, String firstName, String middleName, String lastName, // String paymentMode,
                                String doctorName, Date schdeulledDate, String serviceName, Integer quantity, Double serviceCharges,
                                Double discountRate, Double discountAmount, Double taxRate, Double taxAmount, Double totalAmount) {
        this.invoiceId = invoiceId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
//        this.paymentMode = paymentMode;
        this.paymentMode = "Cash";
        this.doctorName = doctorName;
        this.schdeulledDate = schdeulledDate;
        this.serviceName = serviceName;
        this.quantity = quantity;
        this.serviceCharges = serviceCharges;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.taxRate = taxRate;
        this.taxAmount = taxAmount;
        this.fullName = this.firstName + ((this.middleName == null || this.middleName.trim().equals("")) ? " " : " " + this.middleName + " ") + this.lastName;
        this.totalAmount = this.calculateTotalAmount(serviceCharges, discountRate, taxRate);

        this.invoiceTotalDiscount = discountAmount;
        this.invoiceTotalTax = taxAmount;
        this.invoiceTotalAmount = totalAmount;

    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Date getSchdeulledDate() {
        return schdeulledDate;
    }

    public void setSchdeulledDate(Date schdeulledDate) {
        this.schdeulledDate = schdeulledDate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(Double serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getInvoiceTotalDiscount() {
        return invoiceTotalDiscount;
    }

    public void setInvoiceTotalDiscount(Double invoiceTotalDiscount) {
        this.invoiceTotalDiscount = invoiceTotalDiscount;
    }

    public Double getInvoiceTotalTax() {
        return invoiceTotalTax;
    }

    public void setInvoiceTotalTax(Double invoiceTotalTax) {
        this.invoiceTotalTax = invoiceTotalTax;
    }

    public Double getInvoiceTotalAmount() {
        return invoiceTotalAmount;
    }

    public void setInvoiceTotalAmount(Double invoiceTotalAmount) {
        this.invoiceTotalAmount = invoiceTotalAmount;
    }

    private Double calculateTotalAmount(Double serviceCharges, Double discountRate, Double taxRate) {
        Double totalChargesWithDiscount = serviceCharges - (serviceCharges * discountRate / 100);
        Double totalChargesWithTax = totalChargesWithDiscount + (totalChargesWithDiscount * taxRate / 100);
        return totalChargesWithTax;
    }
}
