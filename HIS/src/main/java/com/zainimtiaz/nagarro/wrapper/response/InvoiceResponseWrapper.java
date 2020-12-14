package com.zainimtiaz.nagarro.wrapper.response;

import com.zainimtiaz.nagarro.model.Invoice;

public class InvoiceResponseWrapper {

    long id;
    String invoiceId;
    String patientName;

    Double paidAmount;
    Double invoiceAmount;
    Double taxAmount;
    Double discountAmount;
    String status;

    private boolean selected = false;
    private boolean useAdvancedBal = false;
    private Double balance;
    private Double totalBill;
    private Double totalPaid;
    private Double discountOnPayment =0.00;
    private Double advanceBalance = 0.00;
    private Double appliedAmount = 0.00;
    private Double dueAmount = 0.00;

    private Double refundAmount = 0.00;

    private Double totalInvoiceAmount;

    public InvoiceResponseWrapper() {    }

    public InvoiceResponseWrapper(Invoice invoice) {
        this.id = invoice.getId();
        this.invoiceId = invoice.getInvoiceId();
        this.patientName = invoice.getPatient().getFirstName()+ " " + invoice.getPatient().getLastName();
        this.paidAmount = invoice.getPaidAmount();
        this.invoiceAmount = invoice.getInvoiceAmount();
        this.status = invoice.getStatus();
        this.taxAmount = invoice.getTaxAmount();
        this.discountAmount = invoice.getDiscountAmount();
        this.totalInvoiceAmount = invoice.getTotalInvoiceAmount();
    //    this.dueAmount = invoice.getTotalInvoiceAmount() - invoice.getPaidAmount();
        this.dueAmount = invoice.getTotalInvoiceAmount() - invoice.getPaidAmount();
        this.refundAmount = invoice.getPatientRefunds().stream().filter(i ->i.getRefundType().equalsIgnoreCase("Invoice")).mapToDouble(i-> i.getRefundAmount()).sum();
    }

    public InvoiceResponseWrapper(Double balance, Double totalBill, Double totalPaid, Double advBalance){
        this.balance = balance;
        this.totalBill = totalBill;
        this.totalPaid = totalPaid;
        this.advanceBalance = advBalance;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(Double totalBill) {
        this.totalBill = totalBill;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Double getAdvanceBalance() {
        return advanceBalance;
    }

    public void setAdvanceBalance(Double advanceBalance) {
        this.advanceBalance = advanceBalance;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getTotalInvoiceAmount() {
        return totalInvoiceAmount;
    }

    public void setTotalInvoiceAmount(Double totalInvoiceAmount) {
        this.totalInvoiceAmount = totalInvoiceAmount;
    }

    public Double getAppliedAmount() {
        return appliedAmount;
    }

    public void setAppliedAmount(Double appliedAmount) {
        this.appliedAmount = appliedAmount;
    }

    public Double getDiscountOnPayment() {
        return discountOnPayment;
    }

    public void setDiscountOnPayment(Double discountOnPayment) {
        this.discountOnPayment = discountOnPayment;
    }

    public Double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isUseAdvancedBal() {
        return useAdvancedBal;
    }

    public void setUseAdvancedBal(boolean useAdvancedBal) {
        this.useAdvancedBal = useAdvancedBal;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }
}
