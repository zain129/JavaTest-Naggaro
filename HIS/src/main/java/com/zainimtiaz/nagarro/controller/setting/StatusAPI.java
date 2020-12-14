package com.zainimtiaz.nagarro.controller.setting;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.Status;
import com.zainimtiaz.nagarro.service.StatusService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.StatusWrapper;
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
import java.util.*;
import java.util.stream.IntStream;

/*
 * @author    : jamal
 * @Date      : 7/31/2018.
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.controller.setting
 * @FileName  : TaxAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@RestController
@RequestMapping("/setting/status")
public class StatusAPI {
    @Autowired
    private StatusService statusService;

    Logger logger = LoggerFactory.getLogger(StatusAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "POST", value = "Create Status",
            notes = "This method will Create Status",
            produces = "application/json", nickname = "Create Status",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Status successfully created", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createStatus(HttpServletRequest request,
                                                 @RequestBody StatusWrapper statusWrapper) {
        logger.info("Create Status API called...");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("status.add.error"));
        response.setResponseCode(ResponseEnum.STATUS_ADD_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            Status statusAlready = statusService.isAlreadyExist(statusWrapper.getName());
            if(HISCoreUtil.isValidObject(statusAlready)){
                response.setResponseMessage(messageBundle.getString("status.already.error"));
                response.setResponseCode(ResponseEnum.STATUS_ALREADY_EXIST_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Status already exist with the same name...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            StatusWrapper statusObj = statusService.saveStatus(statusWrapper);
            if (HISCoreUtil.isValidObject(statusObj)) {
                response.setResponseData(statusObj);
                response.setResponseMessage(messageBundle.getString("status.add.success"));
                response.setResponseCode(ResponseEnum.STATUS_ADD_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("status created successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }


        } catch (Exception ex) {
            logger.error("status Creation Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Paginated Status",
            notes = "This method will return Paginated Status",
            produces = "application/json", nickname = "Get Paginated Status ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated Status fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllStatus(HttpServletRequest request,
                                                 @PathVariable("page") int page,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllStatus paginated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("status.not.found"));
        response.setResponseCode(ResponseEnum.STATUS_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<StatusWrapper> fhistoryData = statusService.getAllStatusesWithPagination(page,pageSize);
            int countStatues = statusService.statusesCount();

            if (!HISCoreUtil.isListEmpty(fhistoryData)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countStatues > pageSize) {
                    int remainder = countStatues % pageSize;
                    int totalPages = countStatues / pageSize;
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

                response.setResponseMessage(messageBundle.getString("status.fetched.success"));
                response.setResponseCode(ResponseEnum.STATUS_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAll status Fetched successfully...");
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

    @ApiOperation(httpMethod = "DELETE", value = "Delete Status",
            notes = "This method will Delete Branch on base of id",
            produces = "application/json", nickname = "Delete Branch ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Status Deleted successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStatus(HttpServletRequest request,
                                          @PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("status.delete.error"));
        response.setResponseCode(ResponseEnum.STATUS_DELETED_SUCCESS.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            Status status = this.statusService.getById(id);

            if (HISCoreUtil.isValidObject(status)) {
                Status  status1 = statusService.deleteStatus(status);
                if(HISCoreUtil.isValidObject(status1)){
                    response.setResponseData(null);
                    response.setResponseMessage(messageBundle.getString("status.delete.success"));
                    response.setResponseCode(ResponseEnum.STATUS_DELETED_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("Status Deleted successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("status.not.found"));
                response.setResponseCode(ResponseEnum.STATUS_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());

                logger.info("Unable to find Status...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Unable to delete Status.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "PUT", value = "Update Status ",
            notes = "This method will Update Status",
            produces = "application/json", nickname = "Update Status",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Status successfully updated", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStatus(HttpServletRequest request,
                                                 @PathVariable("id") long id,
                                                 @RequestBody StatusWrapper statusWrapper) {

        logger.info("update Status API called...");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("status.update.error"));
        response.setResponseCode(ResponseEnum.FAMILY_HISTORY_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            Status alreadyStatus = statusService.getById(id);
            if (HISCoreUtil.isValidObject(alreadyStatus)) {
                logger.info("Status founded...");
                StatusWrapper fmUpdated = statusService.updateStatus(statusWrapper, alreadyStatus);
                /*if (!statusService.isStatusNameOrIdExistsAlready(statusWrapper.getName(),id)) {
                    response.setResponseMessage(messageBundle.getString("status.already.error"));
                    response.setResponseCode(ResponseEnum.STATUS_ALREADY_EXIST_ERROR.getValue());
                    response.setResponseStatus(ResponseEnum.ERROR.getValue());
                    response.setResponseData(null);
                    logger.error("Status already exist with the same name...");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }*/
                if (HISCoreUtil.isValidObject(fmUpdated)) {
                    logger.info("Status Updated...");
                    response.setResponseData(fmUpdated);
                    response.setResponseMessage(messageBundle.getString("status.update.success"));
                    response.setResponseCode(ResponseEnum.STATUS_UPDATE_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("Status updated successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                logger.info("Status not found...");
                response.setResponseMessage(messageBundle.getString("status.not.found"));
                response.setResponseCode(ResponseEnum.FAMILY_HISTORY_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Status not updated...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception ex) {
            logger.error("Update Status Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Search Status",
            notes = "This method will return Status on base of search",
            produces = "application/json", nickname = "Search Status",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Status found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> searchPaginatedStatus(HttpServletRequest request,
                                                     @PathVariable("page") int page,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                     @RequestParam(value = "statusName") String statusName){


        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("status.not.found"));
        response.setResponseCode(ResponseEnum.STATUS_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<StatusWrapper> branchWrappers = statusService.searchByStatusName(statusName,page, pageSize);

            int countStatus = statusService.statusesCount();

            if (!HISCoreUtil.isListEmpty(branchWrappers)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countStatus > pageSize) {
                    int remainder = countStatus % pageSize;
                    int totalPages = countStatus / pageSize;
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
                returnValues.put("data", branchWrappers);

                response.setResponseMessage(messageBundle.getString("status.fetched.success"));
                response.setResponseCode(ResponseEnum.STATUS_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("searched Status Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("searched Status failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "All Statuses",
            notes = "This method will return all Statuses",
            produces = "application/json", nickname = "All Statuses",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Statuses fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/allStatus", method = RequestMethod.GET)
    public ResponseEntity<?> getAllStatuses(HttpServletRequest request) {

        logger.info("getAllStatuses API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("status.fetch.error"));
        response.setResponseCode(ResponseEnum.STATUS_FETCHED_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getAllStatus API - status fetching from DB");
            List<StatusWrapper> branches = statusService.getAllStatuses();
            if (HISCoreUtil.isListEmpty(branches)) {
                response.setResponseMessage(messageBundle.getString("status.not.found"));
                response.setResponseCode(ResponseEnum.STATUS_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("getAllStatuses API - Statuses not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("status.fetch.success"));
            response.setResponseCode(ResponseEnum.STATUS_FETCHED_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(branches);

            logger.info("getAllStatuses API - Statuses successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllStatus API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






}