package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.enums.ResponseEnum;

import com.zainimtiaz.nagarro.model.Organization;
import com.zainimtiaz.nagarro.model.PaymentType;
import com.zainimtiaz.nagarro.service.OrganizationService;
import com.zainimtiaz.nagarro.service.PaymentTypeService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.response.PaymentTypeWrapper;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@RestController
@RequestMapping(value = "/PaymentType")
public class PaymentTypeAPI {

    private final Logger logger = LoggerFactory.getLogger(PaymentTypeAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private PaymentTypeService paymentServiceType;
    @Autowired
    private OrganizationService organizationService;

    @ApiOperation(httpMethod = "GET", value = "All Payment Type",
                notes = "This method will returns all Payment Type",
                produces = "application/json", nickname = "Payment Type",
                response = GenericAPIResponse.class, protocols = "https")
        @ApiResponses({
                @ApiResponse(code = 200, message = "All Payment Type fetched successfully.", response = GenericAPIResponse.class),
                @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
                @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
                @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
                @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
        @RequestMapping(value = "/", method = RequestMethod.GET)
        public ResponseEntity<?> getAllPaymentType(HttpServletRequest request) {

            logger.error("getAll Payment Type API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            logger.error("ALL Payment  API - Payment Type fetching from DB");
//            List<PaymentType> paymentType = paymentServiceType.getAllPaymentType();
            List<PaymentTypeWrapper> paymentType = paymentServiceType.getAllPaymentType();
            Organization dbOrganization = organizationService.getAllOrgizationData();
       //     String Zone = dbOrganization.getZone().getName().replaceAll("\\s", "");
            String systemCurrency = dbOrganization.getCurrencyFormat();
        //    String hoursFormat = dbOrganization.getHoursFormat();
        //    String dateFormat = dbOrganization.getDateFormat();
        //    String timeFormat = dbOrganization.getTimeFormat();
            for(int i=0;i<paymentType.size();i++){

                if (systemCurrency != null || (!systemCurrency.equals(""))) {
                    PaymentTypeWrapper ptw=new PaymentTypeWrapper();
                    ptw.setStrServiceCharges((HISCoreUtil.formatCurrencyDisplay((paymentType.get(i).getServiceCharges()), systemCurrency)));
                    ptw.setStrmaxCardCharges(HISCoreUtil.formatCurrencyDisplay((paymentType.get(i).getMaxCardCharges()), systemCurrency));
                    if(paymentType.get(i).getPaymentMode().equals("Cash") || paymentType.get(i).getPaymentMode().equals("Others")){
                        paymentType.get(i).setStrServiceCharges("");
                        paymentType.get(i).setStrmaxCardCharges("");
                    }else{
                    paymentType.get(i).setStrServiceCharges(ptw.getStrServiceCharges());
                    paymentType.get(i).setStrmaxCardCharges(ptw.getStrmaxCardCharges());
                    }
                    //     msLstW.add(ms);
                }
            }
            if (HISCoreUtil.isListEmpty(paymentType)) {
                response.setResponseMessage(messageBundle.getString("paymentType.not.found"));
                response.setResponseCode(ResponseEnum.PAYMENTTYPE_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Payment Type API  - Payment  not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Map<String, Object> returnValues = new LinkedHashMap<>();
            returnValues.put("data", paymentType);

            response.setResponseMessage(messageBundle.getString("paymentType.fetched.success"));
            response.setResponseCode(ResponseEnum.PAYMENTTYPE_FETCHED_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(returnValues);

            logger.info("Payment Type  API - Payment Type successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Payment Type   API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "List Payment Type",
            notes = "This method will returns List Payment Type",
            produces = "application/json", nickname = "Payment Type",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List Payment Type fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getListPaymentType", method = RequestMethod.GET)
    public ResponseEntity<?> getListPaymentType(HttpServletRequest request) {

        logger.error("getAll Payment Type API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            logger.error("List Payment  API - Payment Type fetching from DB");
            List<PaymentTypeWrapper> paymentType = paymentServiceType.getListPaymentType();

            if (HISCoreUtil.isListEmpty(paymentType)) {
                response.setResponseMessage(messageBundle.getString("paymentType.not.found"));
                response.setResponseCode(ResponseEnum.PAYMENTTYPE_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Payment Type API  - Payment  not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Map<String, Object> returnValues = new LinkedHashMap<>();
            returnValues.put("data", paymentType);

            response.setResponseMessage(messageBundle.getString("paymentType.fetched.success"));
            response.setResponseCode(ResponseEnum.PAYMENTTYPE_FETCHED_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(returnValues);

            logger.info("Payment Type  API - Payment Type List successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Payment Type List  API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///////////// Update Customer////////////////////////////////////////
    @ApiOperation(httpMethod = "PUT", value = "Update Payment Type",
            notes = "This method will Update Payment Type",
            produces = "application/json", nickname = "Update Payment Type",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Payment Type  Updated successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePaymentType(HttpServletRequest request,
                                            @RequestBody PaymentTypeWrapper paymentType) {


        logger.info("Update Payment API - Update Payment Type By id:" + paymentType.getId());
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("paymentType.update.error"));
        response.setResponseCode(ResponseEnum.PAYMENTTYPE_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (HISCoreUtil.isNull(paymentType.getPaymentTitle()) || paymentType.getId()<= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("update Payment Type API - parameter is insufficient...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            paymentServiceType.updatePaymentType(paymentType);
            response.setResponseMessage(messageBundle.getString("paymentType.update.success"));
            response.setResponseCode(ResponseEnum.PAYMENTTYPE_UPDATE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);
            logger.info("update Customer API - Customer updated successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("update Payment Type  API - Exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "POST", value = "Save Payment Type ",
            notes = "This method will Save Payment Type",
            produces = "application/json", nickname = "Save Payment Type ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save Payment Type  successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> savePaymentType(HttpServletRequest request,
                                          @RequestBody PaymentTypeWrapper paymentType) {

        logger.info("API Called...!!");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("paymentType.save.error"));
        response.setResponseCode(ResponseEnum.PAYMENTTYPE_SAVE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {

            if (HISCoreUtil.isNull(paymentType.getPaymentTitle())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("The requested parameter is insufficient...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            /*if(paymentType.getPatient()==null){
                paymentType.setPatient(true);
            }*/
            PaymentType paymentTypeSave = paymentServiceType.savePaymentAPI(paymentType);
            if (HISCoreUtil.isValidObject(paymentTypeSave)) {
                response.setResponseMessage(messageBundle.getString("paymentType.save.success"));
                response.setResponseCode(ResponseEnum.PAYMENTTYPE_SAVE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info("The Payment Type saved successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("save Payment Type exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @ApiOperation(httpMethod = "DELETE", value = "DELETE Payment Type",
            notes = "This method will Delete Payment Type",
            produces = "application/json", nickname = "Delete Payment Type",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Delete Payment Type successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{custId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePaymentType(HttpServletRequest request,
                                            @PathVariable("custId")  Long  cusId) {

        logger.error("Delete Payment Type API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("paymentType.delete.error"));
        response.setResponseCode(ResponseEnum.PAYMENTTYPE_DELETE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("Delete Customer  -  fetching from DB for existence");

            if (cusId <= 0) {
                response.setResponseMessage(messageBundle.getString("cli.dpts.delete.id"));
                response.setResponseCode(ResponseEnum.PAYMENTTYPE_DELETE_ID.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);

                logger.error("delete - Please provide proper Id");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            paymentServiceType.deletePaymentType(cusId);
            response.setResponseMessage(messageBundle.getString("paymentType.delete.success"));
            response.setResponseCode(ResponseEnum.PAYMENTTYPE_DELETE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);

            logger.error("Delete Payment Api - deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("Delete Payment Api exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
