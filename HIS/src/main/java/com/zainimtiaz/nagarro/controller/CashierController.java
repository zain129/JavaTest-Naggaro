package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.enums.CashierMessageEnum;
import com.zainimtiaz.nagarro.enums.InvoiceMessageEnum;
import com.zainimtiaz.nagarro.enums.InvoiceStatusEnum;
import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.Invoice;
import com.zainimtiaz.nagarro.service.AppointmentService;
import com.zainimtiaz.nagarro.service.PatientInvoiceService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.AppointmentWrapper;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.request.PaymentRequestWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/cashier")
public class CashierController {

    @Autowired
    private PatientInvoiceService patientInvoiceService;

    @Autowired
    AppointmentService appointmentService;

    private final Logger logger = LoggerFactory.getLogger(StaffAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");


    @ApiOperation(httpMethod = "GET", value = "Get All Invoice List data",
            notes = "This method will Get All Invoice List data",
            produces = "application/json", nickname = "Invoice List data",
            response = GenericAPIResponse.class, protocols = "https")

    @ApiResponses({
            @ApiResponse(code = 200, message = "All Invoice List data fetch successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getAllInvoices", method = RequestMethod.GET)
    public ResponseEntity<?> getAllInvoice(HttpServletRequest request) {

        long date = System.currentTimeMillis();

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.add.error"));
        response.setResponseCode(CashierMessageEnum.ERROR.getValue());
        response.setResponseStatus(CashierMessageEnum.ERROR.getValue());
        response.setResponseData(null);
        try
        {
            response.setResponseData(patientInvoiceService.getAllInvoice());

            response.setResponseMessage(messageBundle.getString("user.add.success"));
            response.setResponseCode(CashierMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(CashierMessageEnum.SUCCESS.getValue());
            logger.info("All Invoice List data fetch successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("All Invoice List data  not fetched successfully/ Failed.", ex.fillInStackTrace());
            response.setResponseStatus(CashierMessageEnum.ERROR.getValue());
            response.setResponseCode(CashierMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Get All Pending Invoice List data",
            notes = "This method will Get All Pending Invoice List data",
            produces = "application/json", nickname = "Pending Invoices List data",
            response = GenericAPIResponse.class, protocols = "https")

    @ApiResponses({
            @ApiResponse(code = 200, message = "All Pending Invoice List data fetch successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getPendingInvoices", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPendingInvoice(HttpServletRequest request) {

        long date = System.currentTimeMillis();

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.add.error"));
        response.setResponseCode(CashierMessageEnum.ERROR.getValue());
        response.setResponseStatus(CashierMessageEnum.ERROR.getValue());
        response.setResponseData(null);
        try
        {
            response.setResponseData(patientInvoiceService.getInvoiceListByStatus(InvoiceStatusEnum.PENDING.name()));
            response.setResponseMessage(messageBundle.getString("user.add.success"));
            response.setResponseCode(CashierMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(CashierMessageEnum.SUCCESS.getValue());
            logger.info("All Invoice List data fetch successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("All Invoice List data  not fetched successfully/ Failed.", ex.fillInStackTrace());
            response.setResponseStatus(CashierMessageEnum.ERROR.getValue());
            response.setResponseCode(CashierMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "GET", value = "Fetch Appointment",
            notes = "This method will return Appointment on base of id",
            produces = "application/json", nickname = "Get Single Appointment",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Appointment found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getAppointmentByInvoiceId/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAppointmentByInvoiceId(HttpServletRequest request,@PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("appointment.not-found"));
        response.setResponseCode(ResponseEnum.APPT_NOT_FOUND_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        BufferedImage img = null;
        try {
            Invoice invoice = patientInvoiceService.getInvoiceById(id);
            AppointmentWrapper dbAppointment = this.appointmentService.findAppointmentById(invoice.getAppointment().getId());    // this.appointmentService.findById(id);
            dbAppointment.setReceivedAmount(invoice.getPaidAmount());
            dbAppointment.setPatientAdvanceDeposit(invoice.getPatient().getAdvanceBalance());
        //    img = ImageIO.read(new ByteArrayInputStream(HISCoreUtil.getBarCodeImage(String.valueOf(invoice.getInvoiceId()),100,100)));

       //     dbAppointment.setImgBarcode(img);
            dbAppointment.setImg((HISCoreUtil.getBarCodeImage(String.valueOf(invoice.getInvoiceId()),100,100)));
            // invoice.getPatientRefunds().stream().filter(i ->i.getRefundType()=="Invoice").mapToDouble(i-> i.getRefundAmount()).sum()
            dbAppointment.setRefundAmount(invoice.getPatientRefunds().stream().filter(i ->i.getRefundType().equalsIgnoreCase("Invoice")).mapToDouble(i -> i.getRefundAmount()).sum());
            dbAppointment.setReceive_Patient(invoice.getPatient().getReceive_due());
            if (HISCoreUtil.isValidObject(dbAppointment)) {
                response.setResponseData(dbAppointment);
                response.setResponseCode(ResponseEnum.APPT_FOUND_SUCCESS.getValue());
                response.setResponseMessage(messageBundle.getString("appointment.found"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Appointment Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("appointment.not-found"));
                response.setResponseCode(ResponseEnum.APPT_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("Appointment Not Found ...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Appointment Not Found", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "POST", value = "Save Payment against patient Invoice",
            notes = "This method will Save Payment against patient Invoices",
            produces = "application/json", nickname = "Run Payment",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/savePayment", method = RequestMethod.POST)
    public ResponseEntity<?> savePayment(@RequestBody PaymentRequestWrapper paymentRequestWrapper) {

        long date = System.currentTimeMillis();

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.add.error"));
        response.setResponseCode(InvoiceMessageEnum.ERROR.getValue());
        response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
        response.setResponseData(null);

        try
        {
            if(paymentRequestWrapper.getPaidAmount()>0 || (paymentRequestWrapper.getUseAdvancedBal() && paymentRequestWrapper.getUsedAdvanceDeposit() > 0)){
                patientInvoiceService.savePayment(paymentRequestWrapper);
            }

            response.setResponseMessage(messageBundle.getString("user.add.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("User created successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Create User Failed.", ex.fillInStackTrace());
            response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
            response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //     return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
