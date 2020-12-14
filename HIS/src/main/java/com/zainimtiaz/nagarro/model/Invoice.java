package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "INVOICE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @NaturalId
    @Column(name = "INVOICE_ID")
    private String invoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    private Patient patient;

    @OneToOne
    @JoinColumn(name="APPOINTMENT_ID")
    private Appointment appointment;

    @JsonIgnore
    @OneToMany(mappedBy = "invoice")
    private List<InvoiceItems> invoiceItems;

    @OneToMany(mappedBy = "invoice")
    private List<PatientInvoicePayment> patientInvoicePayments;

    @OneToMany(mappedBy = "paymentType")
    private List<PatientRefund> patientRefunds;

    @Column(name = "TAX_AMOUNT", columnDefinition = "double default '0.00'")
    private  Double taxAmount;

    @Column(name = "DISCOUNT_AMOUNT", columnDefinition = "double default '0.00'")
    private  Double discountAmount;

    @Column(name = "PAID_AMOUNT", columnDefinition = "double default '0.00'")
    private  Double paidAmount;

    @Column(name = "INVOICE_AMOUNT", columnDefinition = "double default '0.00'")
    private Double invoiceAmount;

    @Column(name = "DOCTOR_COMMISSION", columnDefinition = "double default '0.00'")
    private Double doctorCommission;

    @Column(name = "TOTAL_INVOICE_AMOUNT", columnDefinition = "double default '0.00'")
    private Double totalInvoiceAmount;


    @Column(name = "STATUS")
    private String status;

    @Column(name = "COMPLETED")
    private Boolean completed;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public List<InvoiceItems> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItems> invoiceItems) {
        this.invoiceItems = invoiceItems;
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

    public List<PatientInvoicePayment> getPatientInvoicePayments() {
        return patientInvoicePayments;
    }

    public void setPatientInvoicePayments(List<PatientInvoicePayment> patientInvoicePayments) {
        this.patientInvoicePayments = patientInvoicePayments;
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


    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }


    public Double getTotalInvoiceAmount() {
        return totalInvoiceAmount;
    }

    public void setTotalInvoiceAmount(Double totalInvoiceAmount) {
        this.totalInvoiceAmount = totalInvoiceAmount;
    }

    public List<PatientRefund> getPatientRefunds() {
        return patientRefunds;
    }

    public void setPatientRefunds(List<PatientRefund> patientRefunds) {
        this.patientRefunds = patientRefunds;
    }

    public Double getDoctorCommission() {
        return doctorCommission;
    }

    public void setDoctorCommission(Double doctorCommission) {
        this.doctorCommission = doctorCommission;
    }
}
