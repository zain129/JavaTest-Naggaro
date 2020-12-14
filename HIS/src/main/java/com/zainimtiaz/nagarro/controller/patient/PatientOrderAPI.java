package com.zainimtiaz.nagarro.controller.patient;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.service.PatientOrderService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.sd.his.wrapper.*;
import com.zainimtiaz.nagarro.wrapper.request.Patient_OrderWrapper_Update;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.Patient_OrderWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.IntStream;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


@RestController
@RequestMapping("/patient/Imageorder")
public class PatientOrderAPI {

    private final Logger logger = LoggerFactory.getLogger(PatientOrderAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");
    @Autowired
    private PatientOrderService patientOrderService;


    @ApiOperation(httpMethod = "POST", value = "Save Order",
            notes = "This method will save the Order.",
            produces = "application/json", nickname = "Save Order",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save Order successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveOrder(HttpServletRequest request,
                                          @RequestPart("myObject") Patient_OrderWrapper orderWrapper,
                                          @RequestPart(name = "img", required = false) MultipartFile[] file) {
        logger.info("saveDocument API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();


        try {

            if (orderWrapper != null) {

                orderWrapper.setListOfFiles(file);

                if (orderWrapper.getPatientId() <= 0) {
                    response.setResponseCode(ResponseEnum.DOCUMENT_SAVE_PATIENT_REQUIRED.getValue());
                    response.setResponseMessage(messageBundle.getString("document.patient.required"));
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.error("saveDocument API - patient required");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                if (this.patientOrderService.isNameDocumentAvailableByPatientId(orderWrapper.getOrder(), orderWrapper.getPatientId())) {
                    response.setResponseCode(ResponseEnum.DOCUMENT_SAVE_NAME_DUBPLUCATE.getValue());
                    response.setResponseMessage(messageBundle.getString("document.save.name.duplicate"));
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.error("saveDocument API - name of document duplicate.");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                this.patientOrderService.saveOrder(orderWrapper);
                response.setResponseCode(ResponseEnum.DOCUMENT_SAVE_SUCCESS.getValue());
                response.setResponseMessage(messageBundle.getString("document.save.success"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("saveDocument API - successfully saved.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            response.setResponseCode(ResponseEnum.DOCUMENT_SAVE_PATIENT_REQUIRED.getValue());
            response.setResponseMessage(messageBundle.getString("document.update.id.required"));
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("saveDocument API - successfully saved.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("saveDocument exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "GET", value = "GET Paginated",
            notes = "This method will return Paginated Date",
            produces = "application/json", nickname = "GET Paginated",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated  documents fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaginatedOrder(HttpServletRequest request,
                                                       @PathVariable("page") int page,
                                                       @RequestParam("patientId") String patientId,
                                                       @RequestParam(value = "pageSize",
                                                               required = false, defaultValue = "10") int pageSize) {

        logger.error("API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {

            logger.error("Fetching from DB");
            if (patientId == null || Long.valueOf(patientId) <= 0) {
                response.setResponseCode(ResponseEnum.DOCUMENT_SAVE_PATIENT_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("document.patient.required"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error(" API - patient required");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Pageable pageable = new PageRequest(page, pageSize);
           // Patient_OrderWrapper orderWrapper
            List<Patient_OrderWrapper> orderWrapper = this.patientOrderService.getPaginatedOrder(pageable, Long.valueOf(patientId));

            int documentWrappersCount = patientOrderService.countPaginatedDocuments();

            logger.error("getPaginatedDocumentation - fetched successfully");

            if (!HISCoreUtil.isListEmpty(orderWrapper)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (documentWrappersCount > pageSize) {
                    int remainder = documentWrappersCount % pageSize;
                    int totalPages = documentWrappersCount / pageSize;
                    if (remainder > 0) {
                        totalPages = totalPages + 1;
                    }
                    pages = new int[totalPages];
                    pages = IntStream.range(0, totalPages).toArray();
                    currPage = page;
                    nextPage = (currPage + 1) != totalPages ? currPage + 1 : null;
                    prePage = currPage > 0 ? currPage : null;
                } else {
                    pages = new int[1];
                    pages[0] = 0;
                    currPage = 0;
                    nextPage = null;
                    prePage = null;
                }

                Map<String, Object> returnValues = new LinkedHashMap<>();
                returnValues.put("nextPage", nextPage);
                returnValues.put("prePage", prePage);
                returnValues.put("currPage", currPage);
                returnValues.put("pages", pages);
                returnValues.put("data", orderWrapper);

                response.setResponseMessage(messageBundle.getString("document.paginated.success"));
                response.setResponseCode(ResponseEnum.DOCUMENT_PAGINATED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getPaginatedDocumentation API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getPaginatedDocumentation exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(httpMethod = "GET", value = "Get Order",
            notes = "This method will Get the Order.",
            produces = "application/json", nickname = "Get ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get Order successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/get", method = RequestMethod.GET)//, consumes = "multipart/form-data"
    public ResponseEntity<?> getOrderById(HttpServletRequest request,
                                             @RequestParam("orderId") long orderId) {
        logger.info(" API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (orderId <= 0) {
                response.setResponseMessage(messageBundle.getString("document.get.id.required"));
                response.setResponseCode(ResponseEnum.DOCUMENT_GET_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error(" API - successfully saved.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setResponseData(this.patientOrderService.getImagesOrderById(orderId));
            response.setResponseMessage(messageBundle.getString("document.get.success"));
            response.setResponseCode(ResponseEnum.DOCUMENT_GET_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("getDocumentById API - successfully saved.");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getDocumentById exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(httpMethod = "PUT", value = "Update Order",
            notes = "This method will Update the Order.",
            produces = "application/json", nickname = "Update Order",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Update Order successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/updateOrder", method = RequestMethod.PUT)//, consumes = "multipart/form-data"
    public ResponseEntity<?> updateDocument(HttpServletRequest request,
                                            @RequestBody Patient_OrderWrapper_Update orderWrapper) {
        logger.info(" API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (orderWrapper.getPatientId() <= 0) {
                response.setResponseCode(ResponseEnum.ORDER_UPDATE_ID_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("patient.image.patient.required"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("updateDocument API - successfully saved.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (orderWrapper.getOrderId() <= 0) {
                response.setResponseCode(ResponseEnum.DOCUMENT_UPDATE_ID_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("patient.image.delete.id.required"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("updateDocument API - successfully saved.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            this.patientOrderService.updateDocument(orderWrapper);
            response.setResponseMessage(messageBundle.getString("document.update.success"));
            response.setResponseCode(ResponseEnum.DOCUMENT_UPDATE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("updateDocument API - successfully saved.");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateDocument exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete Document",
            notes = "This method will Delete the Document",
            produces = "application/json", nickname = "Delete Document ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted Order successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrderById(HttpServletRequest request,
                                                @PathVariable("orderId") long orderId) {
        logger.info("deleteDocumentById API - Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("document.delete.error"));
        response.setResponseCode(ResponseEnum.ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);


        try {
            if (orderId <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.image.delete.id.required"));
                response.setResponseCode(ResponseEnum.ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error(" API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            this.patientOrderService.deleteDocument(orderId);
            response.setResponseMessage(messageBundle.getString("patient.image.Order.delete.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);
            logger.info(" API - Deleted Successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(" API - deleted failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Get Order",
            notes = "This method will Get the Order.",
            produces = "application/json", nickname = "Get ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get Order successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getImages", method = RequestMethod.GET)//, consumes = "multipart/form-data"
    public ResponseEntity<?> getOrderImageById(HttpServletRequest request,
                                          @RequestParam("orderId") long orderId) {
        logger.info(" API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (orderId <= 0) {
                response.setResponseMessage(messageBundle.getString("document.get.id.required"));
                response.setResponseCode(ResponseEnum.DOCUMENT_GET_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error(" API - successfully saved.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setResponseData(this.patientOrderService.getOrderImageById(orderId));
            response.setResponseMessage(messageBundle.getString("document.get.success"));
            response.setResponseCode(ResponseEnum.DOCUMENT_GET_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("getDocumentById API - successfully saved.");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getDocumentById exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


   /* @ApiOperation(httpMethod = "GET", value = "Get Order",
            notes = "This method will Get the Order.",
            produces = "application/json", nickname = "Get ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get Order successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getImagesOrderId/{orderId}", method = RequestMethod.GET)//, consumes = "multipart/form-data"
    public ResponseEntity<?> getOrderImagesById(HttpServletRequest request,
                                                @PathVariable("orderId") int orderId,
                                                @RequestParam("fileName") String fileName
                                                 ) {
        logger.info(" API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (orderId <= 0) {
                response.setResponseMessage(messageBundle.getString("document.get.id.required"));
                response.setResponseCode(ResponseEnum.DOCUMENT_GET_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error(" API - successfully saved.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setResponseData(this.patientOrderService.getOrderImageById(orderId,fileName));
            response.setResponseMessage(messageBundle.getString("document.get.success"));
            response.setResponseCode(ResponseEnum.DOCUMENT_GET_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("getDocumentById API - successfully saved.");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getDocumentById exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

*/

}
