package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.enums.InvoiceMessageEnum;
import com.zainimtiaz.nagarro.service.PatientInvoiceService;
import com.zainimtiaz.nagarro.service.PatientService;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.request.RefundPaymentRequestWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ResourceBundle;

@RestController
@RequestMapping("/refundApi")
public class RefundController {

    @Autowired
    private PatientInvoiceService patientInvoiceService;

    @Autowired
    private PatientService patientService;

    private final Logger logger = LoggerFactory.getLogger(StaffAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");


    @ApiOperation(httpMethod = "POST", value = "Refund Patient Payment",
            notes = "This method will Refund Patient Payment",
            produces = "application/json", nickname = " Refund Patient Payment",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/refundPayment", method = RequestMethod.POST)
    public ResponseEntity<?> refundPayment(@RequestBody RefundPaymentRequestWrapper refundPaymentRequestWrapper) {
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
         /*   if (createAdvancePaymentRequest.getDate().trim().length() == 10) {
                createAdvancePaymentRequest.setDate( patientService.convertDateToGMT( createAdvancePaymentRequest.getDate().trim(), "yyyy-MM-dd" ) );
            } else {
                createAdvancePaymentRequest.setDate( patientService.convertDateToGMT( createAdvancePaymentRequest.getDate().trim(), "E MMM dd yyyy HH:mm:ss" ) );
            }*/
            patientInvoiceService.refundPayment(refundPaymentRequestWrapper);

            response.setResponseData(patientInvoiceService.getRefundList());
            response.setResponseMessage(messageBundle.getString("refundApi.refund.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("Refund Patient Amount successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Refund Patient Amount Failed.", ex.fillInStackTrace());
            response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
            response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "GET", value = "Get  Refund Id",
            notes = "This method will Get Refund Id",
            produces = "application/json", nickname = " Payment Id",
            response = GenericAPIResponse.class, protocols = "https")

    @ApiResponses({
            @ApiResponse(code = 200, message = "Refund Id data found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getRefundId", method = RequestMethod.GET)
    public ResponseEntity<?> getRefundId() {

        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            response.setResponseData(patientInvoiceService.getRefundPrefixId());
            response.setResponseMessage(messageBundle.getString("refundApi.getRefundId.fetched.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("Refund Id fetched successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Refund Id  Fetched Failed.", ex.fillInStackTrace());
            response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
            response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    // TO DO For Refund
    @ApiOperation(httpMethod = "GET", value = "Get Refund List",
            notes = "This method will Get Refund List",
            produces = "application/json", nickname = " patients Refund List",
            response = GenericAPIResponse.class, protocols = "https")

    @ApiResponses({
            @ApiResponse(code = 200, message = "Refund List data found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getRefundList", method = RequestMethod.GET)
    public ResponseEntity<?> getRefundList() {

        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            response.setResponseData(patientInvoiceService.getRefundList());

            response.setResponseMessage(messageBundle.getString("refundApi.refund.list.fetched.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("Refund List data fetched successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Refund List Fetched Failed.", ex.fillInStackTrace());
            response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
            response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
