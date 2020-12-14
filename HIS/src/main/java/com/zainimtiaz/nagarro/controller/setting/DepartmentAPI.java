package com.zainimtiaz.nagarro.controller.setting;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.Department;
import com.zainimtiaz.nagarro.service.DepartmentService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.DepartmentWrapper;
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

/*
 * @author    : Arif Heer
 * @Date      : 04/5/2018
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer    Date       Version  Operation  Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.controller.setting
 * @FileName  : ClinicalDepartmentAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.s
 *
 */
@RestController
@RequestMapping("/setting/department")
public class DepartmentAPI {

    Logger logger = LoggerFactory.getLogger(DepartmentAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(httpMethod = "GET", value = "All Clinical Departments",
            notes = "This method will return All Clinical Department",
            produces = "application/json", nickname = "All Clinical Department",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Clinical Departments fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> departmentsActive(HttpServletRequest request) {

        logger.error("getAllClinicalDepartments API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("cli.dpts.fetch.error"));
        response.setResponseCode(ResponseEnum.CLI_DPT_FETCH_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getAllClinicalDepartments - dpts fetching from DB");
            List<DepartmentWrapper> dpts = departmentService.getDepartmentsActive();
            logger.error("getAllClinicalDepartments - dpts fetched successfully");

            if (HISCoreUtil.isListEmpty(dpts)) {
                response.setResponseMessage(messageBundle.getString("cli.dpts.not.found.error"));
                response.setResponseCode(ResponseEnum.CLI_DPT_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getAllClinicalDepartments API - dpts not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("cli.dpts.fetch.success"));
            response.setResponseCode(ResponseEnum.CLI_DPT_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(dpts);

            logger.error("getAllClinicalDepartments API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllClinicalDepartments exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "All Clinical Departments",
            notes = "This method will return All Clinical Department",
            produces = "application/json", nickname = "All Clinical Department",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Clinical Departments fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/branches", method = RequestMethod.GET)
    public ResponseEntity<?> getDepartmentsByBranchIds(@RequestParam("branchIds") List<Long> branchIds) {

        logger.error("getDepartmentsByBranchIds API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            logger.error("getDepartmentsByBranchIds - dpts fetching from DB");
            List<DepartmentWrapper> dpts = departmentService.getDepartmentsByBranchIds(branchIds);
            logger.error("getDepartmentsByBranchIds - dpts fetched successfully");

            if (HISCoreUtil.isListEmpty(dpts)) {
                response.setResponseMessage(messageBundle.getString("cli.dpts.not.found.errorr"));
                response.setResponseCode(ResponseEnum.CLI_DPT_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getDepartmentsByBranchIds API - dpts not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("cli.dpts.found.succ"));
            response.setResponseCode(ResponseEnum.CLI_DPT_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(dpts);

            logger.error("getDepartmentsByBranchIds API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getDepartmentsByBranchIds exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "GET", value = "Paginated Clinical Departments",
            notes = "This method will return Paginated Clinical Department",
            produces = "application/json", nickname = "Paginated Clinical Department",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated Clinical Departments fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaginatedAllDepartments(HttpServletRequest request,
                                                        @PathVariable("page") int page,
                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        logger.error("getPaginatedAllClinicalDepartments API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("cli.dpts.fetch.error"));
        response.setResponseCode(ResponseEnum.CLI_DPT_FETCH_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getPaginatedAllClinicalDepartments - dpts fetching from DB");
            List<DepartmentWrapper> dpts = departmentService.getPaginatedAllDepartments(page, pageSize);
            int dptsCount = departmentService.countPaginatedAllDepartments();

            logger.error("getPaginatedAllClinicalDepartments - dpts fetched successfully");

            if (!HISCoreUtil.isListEmpty(dpts)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (dptsCount > pageSize) {
                    int remainder = dptsCount % pageSize;
                    int totalPages = dptsCount / pageSize;
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
                returnValues.put("data", dpts);

                response.setResponseMessage(messageBundle.getString("cli.dpts.fetch.success"));
                response.setResponseCode(ResponseEnum.CLI_DPT_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getPaginatedAllClinicalDepartments API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getPaginatedAllClinicalDepartments exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "DELETE Clinical Department",
            notes = "This method will DELETE Clinical Department",
            produces = "application/json", nickname = "DELETE Clinical Department",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "DELETE Clinical Department successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{dptId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDepartment(HttpServletRequest request,
                                              @PathVariable("dptId") long dptId) {

        logger.error("deleteClinicalDepartment API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("cli.dpts.delete.error"));
        response.setResponseCode(ResponseEnum.CLI_DPT_DELETE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("deleteClinicalDepartment - dpt fetching from DB for existence");

            if (dptId <= 0) {
                response.setResponseMessage(messageBundle.getString("cli.dpts.delete.id"));
                response.setResponseCode(ResponseEnum.CLI_DPT_DELETE_DEPART_ID.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);

                logger.error("deleteClinicalDepartment - Please provide proper department id.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (this.departmentService.hasChild(dptId)) {
                response.setResponseMessage(messageBundle.getString("cli.dpts.delete.has.child"));
                response.setResponseCode(ResponseEnum.CLI_DPT_DELETE_HAS_CHILD.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);

                logger.error("deleteClinicalDepartment - department has child record so you cannot delete it. First , delete its child record then you can delete it");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            departmentService.deleteDepartment(dptId);
            response.setResponseMessage(messageBundle.getString("cli.dpts.delete.success"));
            response.setResponseCode(ResponseEnum.CLI_DPT_DELETE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);

            logger.error("deleteClinicalDepartment - deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("deleteClinicalDepartment exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Search Clinical Department",
            notes = "This method will Search Clinical Department",
            produces = "application/json", nickname = "Search Clinical Department",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Search Clinical Department successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> searchDepartment(HttpServletRequest request,
                                              @PathVariable("page") int page,
                                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                              @RequestParam(value = "name") String name) {

        logger.error("searchClinicalDepartment API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("cli.dpts.fetch.error"));
        response.setResponseCode(ResponseEnum.CLI_DPT_FETCH_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (!HISCoreUtil.isValidObject(name)) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("searchClinicalDepartment - insufficient params");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            List<DepartmentWrapper> departments = departmentService.getPageableSearchedDepartment(page, pageSize, name);
            int countSearchedDpt = departmentService.countSearchedClinicalDepartments(name);

            if (HISCoreUtil.isListEmpty(departments)) {

                response.setResponseMessage(messageBundle.getString("cli.dpts.not.found.error"));
                response.setResponseCode(ResponseEnum.CLI_DPT_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("searchClinicalDepartment - Departments not found.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Integer nextPage, prePage, currPage;
            int[] pages;

            if (countSearchedDpt > pageSize) {
                int remainder = countSearchedDpt % pageSize;
                int totalPages = countSearchedDpt / pageSize;
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
            returnValues.put("data", departments);

            response.setResponseMessage(messageBundle.getString("cli.dpts.fetch.success"));
            response.setResponseCode(ResponseEnum.CLI_DPT_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(returnValues);
            logger.info("searchClinicalDepartment - Departments fetched successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("searchClinicalDepartment exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Save Clinical Department",
            notes = "This method will Save Clinical Department",
            produces = "application/json", nickname = "Save Clinical Department",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save Clinical Department successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})

    @RequestMapping(value = "/save", method = RequestMethod.POST,consumes="application/json")
    public ResponseEntity<?> saveDepartment(HttpServletRequest request,
                                            @RequestBody DepartmentWrapper createRequest) {

        logger.info("saveDepartment API Called...!!");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("cli.dpts.save.error"));
        response.setResponseCode(ResponseEnum.CLI_DPT_SAVE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try{
            if (HISCoreUtil.isNull(createRequest.getName())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("The requested parameter is insufficient...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            DepartmentWrapper dbDpt = departmentService.findDepartmentByName(createRequest.getName());
            if (HISCoreUtil.isValidObject(dbDpt)) {
                response.setResponseMessage(messageBundle.getString("cli.dpts.already.exist"));
                response.setResponseCode(ResponseEnum.CLI_DPT_ALREADY_EXIST.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("The Department is Already Exist...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Department dpt = departmentService.saveDepartment(createRequest);
            if (HISCoreUtil.isValidObject(dpt)) {
                response.setResponseMessage(messageBundle.getString("cli.dpts.save.success"));
                response.setResponseCode(ResponseEnum.CLI_DPT_SAVE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info("The Department saved successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("saveDepartment exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(httpMethod = "PUT", value = "Update Clinical Department",
            notes = "This method will Update Clinical Department",
            produces = "application/json", nickname = "Update Clinical Department",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Clinical Department Updated successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDepartment(HttpServletRequest request,
                                              @RequestBody DepartmentWrapper updateRequest) {

        logger.info("updateDepartment API - Update Department By id:" + updateRequest.getId());
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("cli.dpts.update.error"));
        response.setResponseCode(ResponseEnum.CLI_DPT_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (HISCoreUtil.isNull(updateRequest.getName()) || updateRequest.getId() <= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("updateDepartment API - parameter is insufficient...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (!(departmentService.isDepartmentByNameAndNotIdExist(updateRequest.getName(), updateRequest.getId()))) {
                response.setResponseMessage(messageBundle.getString("cli.dpts.already.exist"));
                response.setResponseCode(ResponseEnum.CLI_DPT_ALREADY_EXIST.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("updateDepartment API - Department not found...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            departmentService.updateDepartment(updateRequest);
            response.setResponseMessage(messageBundle.getString("cli.dpts.update.success"));
            response.setResponseCode(ResponseEnum.CLI_DPT_UPDATE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);
            logger.info("updateDepartment API - Department updated successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("updateDepartment API - Exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}