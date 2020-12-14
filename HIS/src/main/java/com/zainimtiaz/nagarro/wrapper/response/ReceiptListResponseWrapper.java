package com.zainimtiaz.nagarro.wrapper.response;

import com.zainimtiaz.nagarro.model.PatientInvoicePayment;
import com.zainimtiaz.nagarro.model.Payment;

import java.util.List;

public class ReceiptListResponseWrapper {

    private long id;
    private String paymentId;
    private String patientName;
    private Double discountAmount;
    private Double advanceUsedAmount;
    private Double paymontAmount;
    private String paymentType;
    private String transactionType;


    public ReceiptListResponseWrapper() {
    }


    public ReceiptListResponseWrapper(Payment pmt) {
        this.paymentId=pmt.getPaymentId();
        this.paymontAmount=pmt.getPaymentAmount();
        if (pmt.getPatientInvoicePayment().size() > 0) {
            this.patientName = pmt.getPatientInvoicePayment().get(0).getPatient().getFirstName() +" "+pmt.getPatientInvoicePayment().get(0).getPatient().getLastName(); ;
            this.discountAmount=getDiscountOnPayment(pmt.getPatientInvoicePayment());
            this.advanceUsedAmount=getAdvancedUsed(pmt.getPatientInvoicePayment());
        }
        this.transactionType = pmt.getTransactionType();
      this.paymentType=  pmt.getReceiptPaymentType().size() >0 ? pmt.getReceiptPaymentType().get(0).getPaymentType().getPaymentTitle(): "N/A";
    }


    private double getDiscountOnPayment(List<PatientInvoicePayment> patientInvoicePayment) {
        double discountTotal = 0.00;
        for (PatientInvoicePayment pip : patientInvoicePayment) {
            discountTotal += (pip.getDiscountAmount() == null ? 0 : pip.getDiscountAmount());
        }
        return discountTotal;
    }

    private double getAdvancedUsed(List<PatientInvoicePayment> patientInvoicePayment) {
        double advUsedTotal = 0.00;
        for (PatientInvoicePayment pip : patientInvoicePayment) {
            advUsedTotal += (pip.getAdvanceAmount() == null ? 0 : pip.getAdvanceAmount());
        }
        return advUsedTotal;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getAdvanceUsedAmount() {
        return advanceUsedAmount;
    }

    public void setAdvanceUsedAmount(Double advanceUsedAmount) {
        this.advanceUsedAmount = advanceUsedAmount;
    }

    public Double getPaymontAmount() {
        return paymontAmount;
    }

    public void setPaymontAmount(Double paymontAmount) {
        this.paymontAmount = paymontAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
