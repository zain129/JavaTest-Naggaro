package com.zainimtiaz.nagarro.controller.patient;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.FamilyHistory;
import com.zainimtiaz.nagarro.service.PatientService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.FamilyHistoryWrapper;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
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
import java.util.stream.IntStream;

@RestController
@RequestMapping("/patient/family")
public class FamilyHistoryAPI {

    @Autowired
    private PatientService patientService;

    private final Logger logger = LoggerFactory.getLogger(FamilyHistoryAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "POST", value = "Create FamilyHistory",
            notes = "This method will Create Family History",
            produces = "application/json", nickname = "Create FamilyHistory",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "FamilyHistory successfully created", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createFamilyHistory(HttpServletRequest request,
                                            @RequestBody FamilyHistoryWrapper familyHistoryWrapper) {
        logger.info("Create FamilyHistory API called...");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("family-history.add.error"));
        response.setResponseCode(ResponseEnum.FAMILY_HISTORY_ADD_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            FamilyHistoryWrapper fmHistory = patientService.saveFamilyHistory(familyHistoryWrapper);
            if (HISCoreUtil.isValidObject(fmHistory)) {
                response.setResponseData(fmHistory);
                response.setResponseMessage(messageBundle.getString("family-history.add.success"));
                response.setResponseCode(ResponseEnum.FAMILY_HISTORY_ADD_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Family History created successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }


        } catch (Exception ex) {
            logger.error("Family History Creation Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


   @ApiOperation(httpMethod = "GET", value = "Paginated FamilyHistory",
            notes = "This method will return Paginated FamilyHistory",
            produces = "application/json", nickname = "Get Paginated FamilyHistory ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated FamilyHistory fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllFamilyHistory(HttpServletRequest request,
                                                     @PathVariable("page") int page,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllFamilyHistory paginated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("family-history.not.found"));
        response.setResponseCode(ResponseEnum.FAMILY_HISTORY_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<FamilyHistoryWrapper> fhistoryData = patientService.getAllFamilyHistory(page,pageSize);
            int countOrders = patientService.familyHistoryCount();

            if (!HISCoreUtil.isListEmpty(fhistoryData)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countOrders > pageSize) {
                    int remainder = countOrders % pageSize;
                    int totalPages = countOrders / pageSize;
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
                returnValues.put("data", fhistoryData);

                response.setResponseMessage(messageBundle.getString("family-history.fetched.success"));
                response.setResponseCode(ResponseEnum.FAMILY_HISTORY_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAll FamilyHistory Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }

           return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated FamilyHistory failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update Family History
    @ApiOperation(httpMethod = "PUT", value = "Update Family History ",
            notes = "This method will Update Family History",
            produces = "application/json", nickname = "Update Family History",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Family History successfully updated", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFamilyHistory(HttpServletRequest request,
                                            @PathVariable("id") long id,
                                            @RequestBody FamilyHistoryWrapper familyHistoryWrapper) {

        logger.info("update Family History API called...");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("family-history.update.error"));
        response.setResponseCode(ResponseEnum.FAMILY_HISTORY_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            FamilyHistory alreadyFamilyHistory = patientService.findFamilyHistoryById(id);
            if (HISCoreUtil.isValidObject(alreadyFamilyHistory)) {
                logger.info("Family History founded...");
                FamilyHistoryWrapper fmUpdated = patientService.updateFamilyHistory(familyHistoryWrapper, alreadyFamilyHistory);
                if (HISCoreUtil.isValidObject(fmUpdated)) {
                    logger.info("Family History Updated...");
                    response.setResponseData(fmUpdated);
                    response.setResponseMessage(messageBundle.getString("family-history.update.success"));
                    response.setResponseCode(ResponseEnum.FAMILY_HISTORY_UPDATE_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("Family History updated successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                logger.info("Family History not found...");
                response.setResponseMessage(messageBundle.getString("family-history.not.found"));
                response.setResponseCode(ResponseEnum.FAMILY_HISTORY_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Family History not updated...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception ex) {
            logger.error("Update Family History Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete Family History",
            notes = "This method will Delete Family History on base of id",
            produces = "application/json", nickname = "Delete Family History ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Family History Deleted successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFamilyHistory(HttpServletRequest request,
                                            @PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("family-history.delete.error"));
        response.setResponseCode(ResponseEnum.FAMILY_HISTORY_DELETED_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            Boolean familyHistory = patientService.deleteFamilyHistory(id);
            if(HISCoreUtil.isValidObject(familyHistory)){
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("family-history.delete.success"));
                response.setResponseCode(ResponseEnum.FAMILY_HISTORY_DELETED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Family History Deleted successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);}

        }

        catch (Exception ex) {
            logger.error("Unable to delete Family History.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //get Family History by patient
    @ApiOperation(httpMethod = "GET", value = "Paginated FamilyHistory",
            notes = "This method will return Paginated FamilyHistory",
            produces = "application/json", nickname = "Get Paginated FamilyHistory ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated FamilyHistory fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "history/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getFamilyHistoryByPatient(HttpServletRequest request,
                                                 @PathVariable("page") int page,
                                                 @RequestParam("name")String name,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllFamilyHistory paginated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("family-history.not.found"));
        response.setResponseCode(ResponseEnum.FAMILY_HISTORY_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<FamilyHistoryWrapper> fhistoryData = patientService.getAllFamilyHistoryByPatient(page,pageSize,Long.valueOf(name));
            int countOrders = patientService.familyHistoryCount();

            if (!HISCoreUtil.isListEmpty(fhistoryData)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countOrders > pageSize) {
                    int remainder = countOrders % pageSize;
                    int totalPages = countOrders / pageSize;
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
                returnValues.put("data", fhistoryData);

                response.setResponseMessage(messageBundle.getString("family-history.fetched.success"));
                response.setResponseCode(ResponseEnum.FAMILY_HISTORY_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAll FamilyHistory Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated FamilyHistory failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(httpMethod = "GET", value = "All FamilyHistory",
            notes = "This method will return All FamilyHistory",
            produces = "application/json", nickname = "Get All FamilyHistory ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All FamilyHistory fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllFamilyHistory(HttpServletRequest request) {
        logger.info("getAllFamilyHistory ..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("family-history.not.found"));
        response.setResponseCode(ResponseEnum.FAMILY_HISTORY_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<FamilyHistoryWrapper> fhistoryData = patientService.findAllFamilyHistory();
            if(!HISCoreUtil.isListEmpty(fhistoryData)){
                response.setResponseMessage(messageBundle.getString("family-history.fetched.success"));
                response.setResponseCode(ResponseEnum.FAMILY_HISTORY_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(fhistoryData);
                logger.info("getAll FamilyHistory Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated FamilyHistory failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
