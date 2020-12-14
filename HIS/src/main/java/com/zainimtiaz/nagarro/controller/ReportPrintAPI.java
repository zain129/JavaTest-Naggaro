package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.enums.InvoiceMessageEnum;
import com.zainimtiaz.nagarro.service.ReportPrintService;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.reports.AdvancePaymentReportWrapper;
import com.zainimtiaz.nagarro.wrapper.reports.InvoiceReportWrapper;
import com.zainimtiaz.nagarro.wrapper.reports.PatientPaymentReportWrapper;
import com.zainimtiaz.nagarro.wrapper.reports.RefundReceiptReportWrapper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/reportPrint")
public class ReportPrintAPI {

    @Autowired
    private ReportPrintService reportPrintService;
    private final Logger logger = LoggerFactory.getLogger(ReportPrintAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "GET", value = "Print Refund Receipt",
            notes = "This method will Print Refund Receipt",
            produces = "application/json", nickname = "Refund Receipt",
            response = GenericAPIResponse.class, protocols = "https")
    @RequestMapping(value = "/refundReceipt/{refundId}", method = RequestMethod.GET)
    public ResponseEntity<?> refundReceipt(@PathVariable("refundId") String refundId) {
        logger.info("refundReceipt initialized successfully...");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            RefundReceiptReportWrapper refundReceiptReportWrapper = reportPrintService.getRefundReceiptData(refundId);

            Map<String, Object> map = reportPrintService.createParamMap(ReportPrintService.PrintReportsEnum.REFUND_RECEIPT, refundReceiptReportWrapper);
            String outFile = reportPrintService.generateReport("patientRefundVoucher", map);

            response.setResponseData(outFile);
            response.setResponseMessage(messageBundle.getString("report.refund.payment.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("Refund Receipt Printed successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Refund Receipt Print Failed...", ex.fillInStackTrace());
            response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
            response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("report.refund.payment.failed"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Print Advance Payment Receipt",
            notes = "This method will Print Advance Payment Receipt",
            produces = "application/json", nickname = "Advance Payment Receipt",
            response = GenericAPIResponse.class, protocols = "https")
    @RequestMapping(value = "/advancePaymentReceipt/{paymentId}", method = RequestMethod.GET)
    public ResponseEntity<?> advancePaymentReceipt(@PathVariable("paymentId") String paymentId) {
        logger.info("refundReceipt initialized successfully...");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            AdvancePaymentReportWrapper advancePaymentReportWrapper = reportPrintService.getAdvancePaymentReceiptData(paymentId);

            Map<String, Object> map = reportPrintService.createParamMap(ReportPrintService.PrintReportsEnum.ADVANCE_PAYMENT_RECEIPT, advancePaymentReportWrapper);
            String outFile = reportPrintService.generateReport("patientAdvanceVoucher", map);

            response.setResponseData(outFile);
            response.setResponseMessage(messageBundle.getString("report.advance.payment.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("Advance Payment Receipt Printed successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Advance Payment Receipt Print Failed...", ex.fillInStackTrace());
            response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
            response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("report.advance.payment.failed"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Print Patient Payment Invoice",
            notes = "This method will Print Patient Payment Invoice",
            produces = "application/json", nickname = "Patient Payment Invoice",
            response = GenericAPIResponse.class, protocols = "https")
    @RequestMapping(value = "/patientPaymentInvoice/{paymentId}", method = RequestMethod.GET)
    public ResponseEntity<?> patientPaymentInvoice(@PathVariable("paymentId") String paymentId) {
        logger.info("patientPaymentInvoice initialized successfully...");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            PatientPaymentReportWrapper patientPaymentReportWrapper = reportPrintService.getPatientPaymentInvoiceData(paymentId);

            if (patientPaymentReportWrapper != null) {

                Map<String, Object> map = reportPrintService.createParamMap(ReportPrintService.PrintReportsEnum.PATIENT_PAYMENT_INVOICE, patientPaymentReportWrapper);
                String outFile = reportPrintService.generateReport("patientPaymentVoucher", map);

                response.setResponseData(outFile);
                response.setResponseMessage(messageBundle.getString("report.patient.payment.invoice.success"));
                response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
                response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
                logger.info("Patient Payment Invoice Printed successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                logger.error("Patient Payment Invoice Print Failed...");
                response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
                response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
                response.setResponseMessage(messageBundle.getString("report.patient.payment.invoice.no.record"));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception ex) {
            logger.error("Patient Payment Invoice Print Failed...", ex.fillInStackTrace());
            response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
            response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("report.patient.payment.invoice.failed"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Print Patient Complete Invoice",
            notes = "This method will Print Patient's Complete Invoice",
            produces = "application/json", nickname = "Patient Invoice",
            response = GenericAPIResponse.class, protocols = "https")
    @RequestMapping(value = "/patientInvoice/{invoiceId}", method = RequestMethod.GET)
    public ResponseEntity<?> patientInvoice(@PathVariable("invoiceId") String invoiceId) {
        logger.info("patientInvoice initialized successfully...");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            List<InvoiceReportWrapper> invoiceReportWrapper = reportPrintService.getPatientInvoiceData(invoiceId);

            if (invoiceReportWrapper != null) {
                Map<String, Object> map = reportPrintService.createParamMap(ReportPrintService.PrintReportsEnum.PATIENT_INVOICE, invoiceReportWrapper);
                String outFile = reportPrintService.generateReport("patientInvoice", map);

                response.setResponseData(outFile);
                response.setResponseMessage(messageBundle.getString("report.patient.invoice.success"));
                response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
                response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
                logger.info("Patient Payment Invoice Printed successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                logger.error("Patient Invoice Print Failed...");
                response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
                response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
                response.setResponseMessage(messageBundle.getString("report.patient.invoice.no.record"));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception ex) {
            logger.error("Patient Invoice Print Failed...", ex.fillInStackTrace());
            response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
            response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("report.patient.invoice.failed"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
