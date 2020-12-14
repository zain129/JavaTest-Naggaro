package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.Organization;
import com.zainimtiaz.nagarro.repository.OrganizationRepository;
import com.zainimtiaz.nagarro.repository.PatientRepository;
import com.zainimtiaz.nagarro.wrapper.reports.AdvancePaymentReportWrapper;
import com.zainimtiaz.nagarro.wrapper.reports.InvoiceReportWrapper;
import com.zainimtiaz.nagarro.wrapper.reports.PatientPaymentReportWrapper;
import com.zainimtiaz.nagarro.wrapper.reports.RefundReceiptReportWrapper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportPrintService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Value("${spring.http.multipart.location}")
    private String tmpFilePath;

    public RefundReceiptReportWrapper getRefundReceiptData(String refundId) {
        return patientRepository.getOneRefundData(refundId);
    }

    public AdvancePaymentReportWrapper getAdvancePaymentReceiptData(String paymentId) {
        return patientRepository.getOneAdvancePaymentData(paymentId);
    }

    public PatientPaymentReportWrapper getPatientPaymentInvoiceData(String paymentId) {
        return patientRepository.getOneInvoicePaymentData(paymentId);
    }

    public List<InvoiceReportWrapper> getPatientInvoiceData(String invoiceId) {
        return patientRepository.getInvoicesData(invoiceId);
    }

    public String generateReport(String reportName, Map<String, Object> parameters) throws JRException, SQLException, IOException, InterruptedException {
        String reportPath = getClass().getClassLoader().getResource( "reports/" + reportName + ".jrxml").getPath();
        String reportId = (String) parameters.getOrDefault("invoiceId", (String) parameters.getOrDefault("paymentId", (String) parameters.get("transId")));
        String pdfPath = tmpFilePath + reportName + "_" + reportId + ".pdf";
        JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource((ArrayList<?>) parameters.get("beanDS"));
        parameters.put("beanCoDataSource", beanColDataSource);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
        File pdfFile = new File(pdfPath);

        if (pdfFile.exists()) {
            Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pdfFile.getAbsolutePath());
            p.waitFor();
        }
        return pdfFile.getCanonicalPath();
    }

    public Map<String, Object> createParamMap(PrintReportsEnum refundReceipt, Object wrapperObject) {
        Map<String, Object> map = new HashMap<>();
        Organization org = organizationRepository.findOne(1L);
        List<Object> collection = new ArrayList<>();
        map.put("logoImg", getClass().getClassLoader().getResource( "reports/logo.gif").getPath());
        map.put("companyName", org.getCompanyName());
        map.put("officePhone", org.getOfficePhone());
        map.put("email", org.getEmail());

        if (refundReceipt == PrintReportsEnum.REFUND_RECEIPT) {
            RefundReceiptReportWrapper refundReceiptReportWrapper = (RefundReceiptReportWrapper) wrapperObject;
            collection.add(refundReceiptReportWrapper);

            map.put("transId", refundReceiptReportWrapper.getTransId());
            map.put("fullName", refundReceiptReportWrapper.getFullName());
            map.put("paymentMethod", refundReceiptReportWrapper.getPaymentMethod());
            map.put("refundDate", refundReceiptReportWrapper.getRefundDate());
            map.put("patientEMR", refundReceiptReportWrapper.getPatientEMR());
            map.put("refundType", refundReceiptReportWrapper.getRefundType());
            map.put("paidAmount", refundReceiptReportWrapper.getPaidAmount());

        } else if (refundReceipt == PrintReportsEnum.ADVANCE_PAYMENT_RECEIPT) {
            AdvancePaymentReportWrapper advancePaymentReportWrapper = (AdvancePaymentReportWrapper) wrapperObject;
            collection.add(advancePaymentReportWrapper);

            map.put("paymentId", advancePaymentReportWrapper.getPaymentId());
            map.put("fullName", advancePaymentReportWrapper.getFullName());
            map.put("paymentDate", advancePaymentReportWrapper.getPaymentDate());
            map.put("patientEMR", advancePaymentReportWrapper.getPatientEMR());
            map.put("description", advancePaymentReportWrapper.getDescription());
            map.put("paidAmount", advancePaymentReportWrapper.getPaidAmount());

        } else if (refundReceipt == PrintReportsEnum.PATIENT_PAYMENT_INVOICE) {
            PatientPaymentReportWrapper patientPaymentReportWrapper = (PatientPaymentReportWrapper) wrapperObject;
            collection.add(patientPaymentReportWrapper);

            map.put("paymentId", patientPaymentReportWrapper.getPaymentId());
            map.put("fullName", patientPaymentReportWrapper.getFullName());
            map.put("paymentDate", patientPaymentReportWrapper.getPaymentDate());
            map.put("patientEMR", patientPaymentReportWrapper.getPatientEMR());
            map.put("paidAmount", patientPaymentReportWrapper.getPaidAmount());
            map.put("paymentMode", "Cash");
            map.put("invoiceId", patientPaymentReportWrapper.getInvoiceId());
            map.put("invoiceAmount", patientPaymentReportWrapper.getInvoiceAmount());
            map.put("discountAmount", patientPaymentReportWrapper.getDiscountAmount());
            map.put("advance", patientPaymentReportWrapper.getAdvance());
            map.put("appliedAmount", patientPaymentReportWrapper.getAppliedAmount());
            map.put("balance", patientPaymentReportWrapper.getBalance());

        } else if (refundReceipt == PrintReportsEnum.PATIENT_INVOICE) {
            List<Object> invoiceReportWrapper = (List<Object>) wrapperObject;
//            collection.add(invoiceReportWrapper);
            collection = invoiceReportWrapper;

            map.put("fullName", ((InvoiceReportWrapper) invoiceReportWrapper.get(0)).getFullName());
            map.put("doctorName", ((InvoiceReportWrapper) invoiceReportWrapper.get(0)).getDoctorName());
            map.put("schdeulledDate", ((InvoiceReportWrapper) invoiceReportWrapper.get(0)).getSchdeulledDate());
            map.put("paymentMethod", ((InvoiceReportWrapper) invoiceReportWrapper.get(0)).getPaymentMode());
            map.put("invoiceId", ((InvoiceReportWrapper) invoiceReportWrapper.get(0)).getInvoiceId());
            map.put("invoiceTotalDiscount", ((InvoiceReportWrapper) invoiceReportWrapper.get(0)).getInvoiceTotalDiscount());
            map.put("invoiceTotalTax", ((InvoiceReportWrapper) invoiceReportWrapper.get(0)).getInvoiceTotalTax());
            map.put("invoiceTotalAmount", ((InvoiceReportWrapper) invoiceReportWrapper.get(0)).getInvoiceTotalAmount());

        }

        map.put("beanDS", collection);
        return map;
    }

    public enum PrintReportsEnum {
        REFUND_RECEIPT, ADVANCE_PAYMENT_RECEIPT, PATIENT_PAYMENT_INVOICE, PATIENT_INVOICE
    }
}
