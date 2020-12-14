//package com.sd.his.controller.customer;
//
//import com.sd.his.controller.patient.PatientAPI;
//import com.sd.his.enums.ResponseEnum;
//import com.sd.his.model.CustomerEntity;
//import com.sd.his.model.Department;
//import com.sd.his.service.CustomerService;
//import com.sd.his.service.PatientService;
//import com.sd.his.utill.HISCoreUtil;
//import com.sd.his.wrapper.DepartmentWrapper;
//import com.sd.his.wrapper.GenericAPIResponse;
//import com.sd.his.wrapper.PatientWrapper;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//import java.util.ResourceBundle;
//
//@RestController
//@RequestMapping(value = "/Customer")
//public class CustomerAPI {
//
//    private final Logger logger = LoggerFactory.getLogger(CustomerAPI.class);
//    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");
//
//    @Autowired
//
//    private CustomerService customerService;
//
//
//    @ApiOperation(httpMethod = "GET", value = "All Customer",
//            notes = "This method will returns all Customer",
//            produces = "application/json", nickname = "All Customer",
//            response = GenericAPIResponse.class, protocols = "https")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "All Customer fetched successfully.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public ResponseEntity<?> getAllCustomerAction(HttpServletRequest request) {
//
//        logger.error("getAll Customer API initiated");
//        GenericAPIResponse response = new GenericAPIResponse();
//        response.setResponseMessage(messageBundle.getString("customer.fetch.error"));
//        response.setResponseCode(ResponseEnum.CUSTOMER_FETCHED_ERROR.getValue());
//        response.setResponseStatus(ResponseEnum.ERROR.getValue());
//        response.setResponseData(null);
//
//        try {
//            logger.error("get ALL CUSTOMER API - Customer fetching from DB");
//            List<CustomerEntity> customerList = customerService.getAllCustomer();
//
//            if (HISCoreUtil.isListEmpty(customerList)) {
//                response.setResponseMessage(messageBundle.getString("Customer.not.found"));
//                response.setResponseCode(ResponseEnum.CUSTOMER_NOT_FOUND_ERROR.getValue());
//                response.setResponseStatus(ResponseEnum.ERROR.getValue());
//                response.setResponseData(null);
//                logger.error("getAllCustomer API  - customer not found");
//
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//
//            response.setResponseMessage(messageBundle.getString("customer.fetched.success"));
//            response.setResponseCode(ResponseEnum.CUSTOMER_FETCHED_SUCCESS.getValue());
//            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
//            response.setResponseData(customerList);
//
//            logger.error("getAllCustomer API - Customer successfully fetched.");
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception ex) {
//            logger.error("getAllCustomer  API -  exception..", ex.fillInStackTrace());
//            response.setResponseStatus(ResponseEnum.ERROR.getValue());
//            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
//            response.setResponseMessage(messageBundle.getString("exception.occurs"));
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    ///////////// Update Customer////////////////////////////////////////
//    @ApiOperation(httpMethod = "PUT", value = "Update Customer",
//            notes = "This method will Update Customer",
//            produces = "application/json", nickname = "Update Customer",
//            response = GenericAPIResponse.class, protocols = "https")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Customer Updated successfully.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
//
//    @RequestMapping(value = "/update", method = RequestMethod.PUT)
//    public ResponseEntity<?> updateCustomer(HttpServletRequest request,
//                                              @RequestBody CustomerEntity customerEntity) {
//
//
//        logger.info("updateCustomer API - Update Customer By id:" + customerEntity.getCustomerId());
//        GenericAPIResponse response = new GenericAPIResponse();
//        response.setResponseMessage(messageBundle.getString("customer.update.error"));
//        response.setResponseCode(ResponseEnum.CUSTOMER_UPDATE_ERROR.getValue());
//        response.setResponseStatus(ResponseEnum.ERROR.getValue());
//        response.setResponseData(null);
//
//        try {
//            if (HISCoreUtil.isNull(customerEntity.getFirstName()) || customerEntity.getCustomerId() <= 0) {
//                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
//                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
//                response.setResponseStatus(ResponseEnum.ERROR.getValue());
//                response.setResponseData(null);
//                logger.info("updateCustomer API - parameter is insufficient...");
//
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//
//            if (customerEntity.getFirstName() != null && !customerEntity.getFirstName().isEmpty() && customerService.isNameAlreadyExists(customerEntity.getFirstName(),customerEntity.getLastName())) {
//                response.setResponseMessage(messageBundle.getString("customer.name.already-found.error"));
//                response.setResponseCode(ResponseEnum.CUSTOMER_ALREADY_EXIST_ERROR.getValue());
//                response.setResponseStatus(ResponseEnum.ERROR.getValue());
//                response.setResponseData(null);
//                logger.error("Customer API - Name already found.");
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//            customerService.updateCustomer(customerEntity);
//            response.setResponseMessage(messageBundle.getString("customer.update.success"));
//            response.setResponseCode(ResponseEnum.CUSTOMER_UPDATE_SUCCESS.getValue());
//            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
//            response.setResponseData(null);
//            logger.info("update Customer API - Customer updated successfully...");
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception ex) {
//            logger.error("update Customer  API - Exception..", ex.fillInStackTrace());
//            response.setResponseStatus(ResponseEnum.ERROR.getValue());
//            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
//            response.setResponseMessage(messageBundle.getString("exception.occurs"));
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//
//    @ApiOperation(httpMethod = "POST", value = "Save Customer",
//            notes = "This method will Save Customer ",
//            produces = "application/json", nickname = "Save Customer ",
//            response = GenericAPIResponse.class, protocols = "https")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Save Customer successfully.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
//
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public ResponseEntity<?> saveCustomer(HttpServletRequest request,
//                                            @RequestBody CustomerEntity createRequest) {
//
//        logger.info("Customer API Called...!!");
//        GenericAPIResponse response = new GenericAPIResponse();
//        response.setResponseMessage(messageBundle.getString("customer.save.error"));
//        response.setResponseCode(ResponseEnum.CUSTOMER_SAVE_ERROR.getValue());
//        response.setResponseStatus(ResponseEnum.ERROR.getValue());
//        response.setResponseData(null);
//
//        try {
//
//            if (HISCoreUtil.isNull(createRequest.getFirstName())) {
//                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
//                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
//                response.setResponseStatus(ResponseEnum.ERROR.getValue());
//                response.setResponseData(null);
//                logger.info("The requested parameter is insufficient...");
//
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//            if (createRequest.getFirstName() != null && !createRequest.getFirstName().isEmpty() && customerService.isNameAlreadyExists(createRequest.getFirstName(),createRequest.getLastName())) {
//                response.setResponseMessage(messageBundle.getString("customer.name.already-found.error"));
//                response.setResponseCode(ResponseEnum.CUSTOMER_ALREADY_EXIST_ERROR.getValue());
//                response.setResponseStatus(ResponseEnum.ERROR.getValue());
//                response.setResponseData(null);
//                logger.error("Customer API - Name already found.");
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//            CustomerEntity customerEntity = customerService.saveCustomer(createRequest);
//            if (HISCoreUtil.isValidObject(customerEntity)) {
//                response.setResponseMessage(messageBundle.getString("customer.save.success"));
//                response.setResponseCode(ResponseEnum.CUSTOMER_SAVE_SUCCESS.getValue());
//                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
//                response.setResponseData(null);
//                logger.info("The Customer saved successfully...");
//
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//        } catch (Exception ex) {
//            logger.error("save Customer exception..", ex.fillInStackTrace());
//            response.setResponseStatus(ResponseEnum.ERROR.getValue());
//            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
//            response.setResponseMessage(messageBundle.getString("exception.occurs"));
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//
//
//    @ApiOperation(httpMethod = "DELETE", value = "DELETE CUSTOMER",
//            notes = "This method will Delete Customer",
//            produces = "application/json", nickname = "Delete Customer",
//            response = GenericAPIResponse.class, protocols = "https")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Delete Customer successfully.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
//            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
//    @RequestMapping(value = "/delete/{custId}", method = RequestMethod.DELETE)
//    public ResponseEntity<?> deleteCustomer(HttpServletRequest request,
//                                              @PathVariable("custId")  long  cusId) {
//
//        logger.error("delete Customer API initiated");
//        GenericAPIResponse response = new GenericAPIResponse();
//        response.setResponseMessage(messageBundle.getString("customer.delete.error"));
//        response.setResponseCode(ResponseEnum.CUSTOMER_DELETE_ERROR.getValue());
//        response.setResponseStatus(ResponseEnum.ERROR.getValue());
//        response.setResponseData(null);
//
//        try {
//            logger.error("delete Customer  -  fetching from DB for existence");
//
//            if (cusId <= 0) {
//                response.setResponseMessage(messageBundle.getString("cli.dpts.delete.id"));
//                response.setResponseCode(ResponseEnum.CUSTOMER_DELETE_ID.getValue());
//                response.setResponseStatus(ResponseEnum.WARN.getValue());
//                response.setResponseData(null);
//
//                logger.error("delete - Please provide proper Id");
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//
//            customerService.deleteCustomer(cusId);
//            response.setResponseMessage(messageBundle.getString("customer.delete.success"));
//            response.setResponseCode(ResponseEnum.CUSTOMER_DELETE_SUCCESS.getValue());
//            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
//            response.setResponseData(null);
//
//            logger.error("delete Customer - deleted successfully.");
//            return new ResponseEntity<>(response, HttpStatus.OK);
//
//        } catch (Exception ex) {
//            logger.error("delete Customer exception..", ex.fillInStackTrace());
//            response.setResponseStatus(ResponseEnum.ERROR.getValue());
//            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
//            response.setResponseMessage(messageBundle.getString("exception.occurs"));
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
