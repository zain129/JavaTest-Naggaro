package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.enums.InvoiceMessageEnum;
import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.service.PatientInvoiceService;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.request.GenerateInvoiceRequestWrapper;
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
import java.util.ResourceBundle;

@RestController
@RequestMapping("/invoice")
public class PatientInvoiceController {

    @Autowired
    private PatientInvoiceService patientInvoiceService;

    private final Logger logger = LoggerFactory.getLogger(StaffAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");


    @ApiOperation(httpMethod = "POST", value = "Create patient Invoice",
            notes = "This method will Create  patient Invoices",
            produces = "application/json", nickname = " patient Invoice",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/saveInvoice", method = RequestMethod.POST)
    public ResponseEntity<?> saveInvoice(HttpServletRequest request, @RequestBody GenerateInvoiceRequestWrapper createInvoiceRequest) {

        long date = System.currentTimeMillis();

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.add.error"));
        response.setResponseCode(InvoiceMessageEnum.ERROR.getValue());
        response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
        response.setResponseData(null);

        try
        {
            patientInvoiceService.saveInvoice(createInvoiceRequest);

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

    @ApiOperation(httpMethod = "GET", value = "Get Invoice Items By patient Appointment Id",
            notes = "This method will Get Invoice Items By patient Appointment Id",
            produces = "application/json", nickname = " patient Invoice Items",
            response = GenericAPIResponse.class, protocols = "https")

    @ApiResponses({
            @ApiResponse(code = 200, message = "Invoice Items data found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getInvoiceItemsById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getInvoiceItemsById(HttpServletRequest request, @PathVariable("id") long  invoiceID) {

        long date = System.currentTimeMillis();

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.add.error"));
        response.setResponseCode(InvoiceMessageEnum.ERROR.getValue());
        response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
        response.setResponseData(null);
        try
        {
            response.setResponseData(patientInvoiceService.getInvoiceItemsById(invoiceID));

            response.setResponseMessage(messageBundle.getString("user.add.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("patient Invoioce data fetched successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("patient Invoioce dat Failed.", ex.fillInStackTrace());
            response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
            response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Get patient Invoices Balance",
            notes = "This method will Get patient Invoices Balance",
            produces = "application/json", nickname = " patient Invoice",
            response = GenericAPIResponse.class, protocols = "https")
    @RequestMapping(value = "/getPatientInvBal/{patientId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientInvoicesBalance(@PathVariable ("patientId") Long patientId) {
        long date = System.currentTimeMillis();
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("invoice.balance.error"));
        response.setResponseCode(InvoiceMessageEnum.ERROR.getValue());
        response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            response.setResponseData( patientInvoiceService.getPatientInvoicesBalance(patientId) );
            response.setResponseMessage(messageBundle.getString("invoice.balance.found"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("Patient Invoices Balance fetched successfully...");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex) {
            logger.error("Patient Invoices Balance fetching Failed.", ex.fillInStackTrace());
            response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
            response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //     return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Generate Invoice On CheckIn",
            notes = "This method will Generate Invoice On CheckIn against appointId",
            produces = "application/json", nickname = "Generate Invoice On CheckIn",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Generate Invoice On CheckIn successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/generateInvoiceOnCheckIn/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> generateInvoiceOnCheckIn(HttpServletRequest request, @PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("invoice.status.not.found"));
        response.setResponseCode(ResponseEnum.INVOICE_NOT_GENERATED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            patientInvoiceService.generateInvoiceOnCheckIn(id);
            response.setResponseCode(ResponseEnum.INVOICE_GENERATED_SUCCESS.getValue());
            response.setResponseMessage(messageBundle.getString("invoice.status.found"));
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Invoice generate successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Invoive Not Found", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
