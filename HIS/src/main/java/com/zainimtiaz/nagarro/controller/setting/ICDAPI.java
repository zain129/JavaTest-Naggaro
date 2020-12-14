package com.zainimtiaz.nagarro.controller.setting;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.ICDCodeVersion;
import com.zainimtiaz.nagarro.model.ICDVersion;
import com.zainimtiaz.nagarro.service.BulkImportService;
import com.zainimtiaz.nagarro.service.ICDService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.ICDCodeVersionWrapper;
import com.zainimtiaz.nagarro.wrapper.ICDCodeWrapper;
import com.zainimtiaz.nagarro.wrapper.ICDVersionWrapper;
import com.zainimtiaz.nagarro.wrapper.ICDCodeCreateRequest;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

/*
 * @author    : Irfan Nasim
 * @Date      : 26-Apr-18
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
 * @FileName  : ICDController
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@RequestMapping("/setting/icd")
@RestController
public class ICDAPI {

    @Autowired
    private ICDService icdService;
    @Autowired
    private BulkImportService bulkImportService;
    private final Logger logger = LoggerFactory.getLogger(ICDAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "GET", value = "versions",
            notes = "This method will return   Versions ",
            produces = "application/json", nickname = " versions",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "versions fetched", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/versions", method = RequestMethod.GET)
    public ResponseEntity<?> getAllICDVersions(HttpServletRequest request) {

        logger.info("getAllICDVersions API initiated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.versions.not.found"));
        response.setResponseCode(ResponseEnum.ICD_VERSION_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ICD_VERSION_ERROR.getValue());
        response.setResponseData(null);
        try {

            logger.info("Versions Found Successfully");
            response.setResponseData(icdService.versios());
            response.setResponseMessage(messageBundle.getString("icd.versions.found.success"));
            response.setResponseCode(ResponseEnum.ICD_VERSIONS_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("getAllICDVersions failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "versions",
            notes = "This method will return   Versions ",
            produces = "application/json", nickname = " versions",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "versions fetched", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/versions/dataTable", method = RequestMethod.GET)
    public ResponseEntity<?> getAllICDVersionsForDataTable() {

        logger.info("getAllICDVersionsForDataTable API initiated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.versions.not.found"));
        response.setResponseCode(ResponseEnum.ICD_VERSION_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ICD_VERSION_ERROR.getValue());
        response.setResponseData(null);

        try {

            logger.info("getAllICDVersionsForDataTable Found Successfully");
            response.setResponseData(icdService.versiosForDataTable());
            response.setResponseMessage(messageBundle.getString("icd.versions.found.success"));
            response.setResponseCode(ResponseEnum.ICD_VERSIONS_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("getAllICDVersionsForDataTable failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "codes Not Deleted ",
            notes = "This method will return codes Not Deleted",
            produces = "application/json", nickname = "codes Not Deleted",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "codes Not Deleted fetched", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/codes/dataTable", method = RequestMethod.GET)
    public ResponseEntity<?> getAllICDCodesForDataTable() {

        logger.info("getAllICDCodesForDataTable API initiated..");

        GenericAPIResponse response = new GenericAPIResponse();

        try {

            response.setResponseData(icdService.codesForDataTable());
            response.setResponseMessage(messageBundle.getString("icd.codes.found.success"));
            response.setResponseCode(ResponseEnum.ICD_CODE_VERSION_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("getAllICDCodesForDataTable failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "codes Not Deleted ",
            notes = "This method will return codes Not Deleted",
            produces = "application/json", nickname = "codes Not Deleted",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "codes Not Deleted fetched", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/codes", method = RequestMethod.GET)
    public ResponseEntity<?> getAllICDCodes(HttpServletRequest request) {

        logger.info("codes API initiated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.codes.not.found"));
        response.setResponseCode(ResponseEnum.ICD_VERSION_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {

            logger.info("codes Not Deleted Found Successfully");
            response.setResponseData(icdService.codes());
            response.setResponseMessage(messageBundle.getString("icd.codes.found.success"));
            response.setResponseCode(ResponseEnum.ICD_CODE_VERSION_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("codes failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "get All Code Versions",
            notes = "This method will return code Versions",
            produces = "application/json", nickname = "codeVersions ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "code Versions fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/codeVersions/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCodeVersions(HttpServletRequest request,
                                                @PathVariable("page") int page,
                                                @RequestParam(value = "pageSize",
                                                        required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllCodeVersions API Initiated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.code.version.not-found"));
        response.setResponseCode(ResponseEnum.ICD_CODE_VERSION_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<ICDCodeVersionWrapper> cvs = icdService.codeVersions(page, pageSize);
            int countICDs = icdService.countCodeVersions();

            if (!HISCoreUtil.isListEmpty(cvs)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countICDs > pageSize) {
                    int remainder = countICDs % pageSize;
                    int totalPages = countICDs / pageSize;
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
                returnValues.put("data", cvs);

                response.setResponseMessage(messageBundle.getString("icd.code.version.found"));
                response.setResponseCode(ResponseEnum.ICD_CODE_VERSION_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("code Versions Fetched successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("fetching code Versions failed.", ex.fillInStackTrace());
            response.setResponseData(null);
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "saveCode ",
            notes = "This method will return Status of Code",
            produces = "application/json", nickname = "saveCode",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "save Code successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/code/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveICDCode(HttpServletRequest request,
                                         @RequestBody ICDCodeCreateRequest createRequest) {
        logger.info("saveCode API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseData(null);
        response.setResponseMessage(messageBundle.getString("icd.code.save.error"));
        response.setResponseCode(ResponseEnum.ICD_CODE_SAVE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());

        try {

            if (HISCoreUtil.isNull(createRequest.getCode())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("saveCode API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (icdService.isICDCodeAlreadyExist(createRequest.getCode())) {
                response.setResponseMessage(messageBundle.getString("icd.code.already.exist"));
                response.setResponseCode(ResponseEnum.ICD_CODE_ALREADY_EXIST_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            String message = icdService.saveICDCode(createRequest);
            if (HISCoreUtil.isValidObject(message)) {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("icd.code.save.success"));
                response.setResponseCode(ResponseEnum.ICD_CODE_SAVE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("saveCode API - saving failed.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "PUT", value = "Update ICD Code  ",
            notes = "This method will return Status of ICDCode",
            produces = "application/json", nickname = "Update  ICDCode",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Update ICDCode  successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/code/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateICDCode(HttpServletRequest request,
                                           @RequestBody ICDCodeCreateRequest createRequest) {
        logger.info("updateICDCode API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseData(null);
        response.setResponseMessage(messageBundle.getString("icd.code.update.error"));
        response.setResponseCode(ResponseEnum.ICD_CODE_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());

        try {

            if (createRequest.getId() <= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateICDCode API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (HISCoreUtil.isNull(createRequest.getCode())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateICDCode API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (icdService.isICDCodeAlreadyExistAgainstICDCodeId(createRequest.getCode(), createRequest.getId())) {
                response.setResponseMessage(messageBundle.getString("icd.code.already.exist"));
                response.setResponseCode(ResponseEnum.ICD_CODE_ALREADY_EXIST_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            icdService.updateICDCode(createRequest);
            response.setResponseData(null);
            response.setResponseMessage(messageBundle.getString("icd.update.success"));
            response.setResponseCode(ResponseEnum.ICD_CODE_UPDATE_SUCC.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateICDCode failed.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return Response with all paginated ICDCode Versions.
     * @author Irfan Nasim
     * @description API will return detail of all ICDCode Versions paginated.
     * @since 30-04-2017
     */
    @ApiOperation(httpMethod = "GET", value = "Paginated ICDs",
            notes = "This method will return Paginated ICDs",
            produces = "application/json", nickname = "Get Paginated ICDs ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated ICDs fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/codes/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaginatedICDCodes(HttpServletRequest request,
                                                  @PathVariable("page") int page,
                                                  @RequestParam(value = "pageSize",
                                                          required = false, defaultValue = "10") int pageSize) {
        logger.info("get all ICD Code paginated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.not-found"));
        response.setResponseCode(ResponseEnum.ICD_CODE_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            List<ICDCodeWrapper> icdsWrappers = icdService.findCodes(page, pageSize);
            int countICDs = icdService.countCodes();

            if (!HISCoreUtil.isListEmpty(icdsWrappers)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countICDs > pageSize) {
                    int remainder = countICDs % pageSize;
                    int totalPages = countICDs / pageSize;
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
                returnValues.put("data", icdsWrappers);

                response.setResponseMessage(messageBundle.getString("icd.fetched.success"));
                response.setResponseCode(ResponseEnum.ICD_CODE_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("ICD Code Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated ICD code failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return Response with all search Filtered ICDs.
     * @author Jamal Nasim
     * @description API will return detail of all filtered ICDs.
     * @since 02-05-2017
     */
    @ApiOperation(httpMethod = "GET", value = "Search ICDs",
            notes = "This method will return Searched ICDs",
            produces = "application/json", nickname = "Get Searched ICDs ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Searched ICDs fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/code/search/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> searchICDCodes(HttpServletRequest request,
                                            @PathVariable("page") int pageNo,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                            @RequestParam(value = "code") String code) {

        logger.info("get all Searched ICDs");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.not-found"));
        response.setResponseCode(ResponseEnum.ICD_CODE_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<ICDCodeWrapper> icds = icdService.searchCodes(code, pageNo, pageSize);
            int icdCount = icdService.countSearchCodes(code);

            if (!HISCoreUtil.isListEmpty(icds)) {
                logger.info("ICDs fetched from DB successfully...");
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (icdCount > pageSize) {
                    int remainder = icdCount % pageSize;
                    int totalPages = icdCount / pageSize;
                    if (remainder > 0) {
                        totalPages = totalPages + 1;
                    }
                    pages = new int[totalPages];
                    pages = IntStream.range(0, totalPages).toArray();
                    currPage = pageNo;
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
                returnValues.put("data", icds);

                response.setResponseMessage(messageBundle.getString("icd.fetched.success"));
                response.setResponseCode(ResponseEnum.ICD_CODE_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("All Searched ICDs fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("get all filtered admins failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete ICDCode",
            notes = "This method will return Deleted Status of ICDCode",
            produces = "application/json", nickname = "Delete ICDCode ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted ICDCode successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/code/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteICDCode(HttpServletRequest request,
                                           @RequestParam("codeId") Long codeId) {
        logger.info("Delete ICDCode Api Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.delete.error"));
        response.setResponseCode(ResponseEnum.ICD_CODE_DELETE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (codeId <= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("deleteICD API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (this.icdService.isCodeAssociated(codeId)) {
                response.setResponseMessage(messageBundle.getString("icd.code.delete.has.child"));
                response.setResponseCode(ResponseEnum.ICD_CODE_HAS_CHILD.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("deleteICD API - ICD CODE has child record so record will not delete. First delete child record then you can delete it");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (icdService.deletedICD(codeId)) {
                response.setResponseMessage(messageBundle.getString("icd.delete.success"));
                response.setResponseCode(ResponseEnum.ICD_CODE_DELETE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info("ICDs Deleted Successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseMessage(messageBundle.getString("icd.already.deleted"));
                response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("ICDs Already Deleted...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Delete ICDCode failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Save ICD Version ",
            notes = "This method will return Status while saving ICD Version",
            produces = "application/json", nickname = "Save ICD Version",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save ICD Version successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/version/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveICDVersion(HttpServletRequest request,
                                            @RequestBody ICDVersionWrapper createRequest) {
        logger.info("saveICDVersion API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseData(null);
        response.setResponseMessage(messageBundle.getString("icd.version.save.error"));
        response.setResponseCode(ResponseEnum.ICD_VERSION_SAVE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());

        try {

            if (HISCoreUtil.isNull(createRequest.getName())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("saveICDVersion API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (icdService.isICDVersionNameAlreadyExist(createRequest.getName())) {
                response.setResponseMessage(messageBundle.getString("icd.code.already.exist"));
                response.setResponseCode(ResponseEnum.ICD_VERSION_ALREADY_EXIST_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.error("saveICDVersion API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            ICDVersion icd = icdService.saveICDVersion(createRequest);
            if (HISCoreUtil.isValidObject(icd)) {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("icd.version.save.success"));
                response.setResponseCode(ResponseEnum.ICD_VERSION_SAVE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("saveICDVersion failed.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Paginated Versions",
            notes = "This method will return Paginated  Versions",
            produces = "application/json", nickname = "Get Paginated  Versions",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated  Versions fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/versions/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaginatedVersions(HttpServletRequest request,
                                                     @PathVariable("page") int page,
                                                     @RequestParam(value = "pageSize",
                                                             required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllPaginatedVersions API called..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.version.not-found"));
        response.setResponseCode(ResponseEnum.ICD_VERSIONS_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<ICDVersionWrapper> icdsWrappers = icdService.findVersions(page, pageSize);
            int countICDsVersion = icdService.countVersion();

            if (!HISCoreUtil.isListEmpty(icdsWrappers)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countICDsVersion > pageSize) {
                    int remainder = countICDsVersion % pageSize;
                    int totalPages = countICDsVersion / pageSize;
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
                returnValues.put("data", icdsWrappers);

                response.setResponseMessage(messageBundle.getString("icd.versions.fetched.success"));
                response.setResponseCode(ResponseEnum.ICD_CODE_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAllPaginatedICDVersions Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get All Paginated ICD Versions failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return Response with all search Filtered ICDs.
     * @author Jamal
     * @description API will return detail of all filtered ICDs.
     * @since 02-05-2017
     */
    @ApiOperation(httpMethod = "GET", value = "Search ICDs Version",
            notes = "This method will return Searched ICDs Version",
            produces = "application/json", nickname = "Get Searched ICDs Version",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Searched ICDs  Version fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/version/search/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> searchVersions(HttpServletRequest request,
                                            @PathVariable("page") int pageNo,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                            @RequestParam(value = "searchVersion") String searchVersion) {

        logger.info("searchVersions API Called");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.versions.search.error"));
        response.setResponseCode(ResponseEnum.ICD_VERSIONS_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<ICDVersionWrapper> icds = icdService.searchByVersion(searchVersion, pageNo, pageSize);
            int icdVersionCount = this.icdService.countSearchByVersion(searchVersion);

            if (!HISCoreUtil.isListEmpty(icds)) {
                logger.info("ICDs Version fetched from DB successfully...");
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (icdVersionCount > pageSize) {
                    int remainder = icdVersionCount % pageSize;
                    int totalPages = icdVersionCount / pageSize;
                    if (remainder > 0) {
                        totalPages = totalPages + 1;
                    }
                    pages = new int[totalPages];
                    pages = IntStream.range(0, totalPages).toArray();
                    currPage = pageNo;
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
                returnValues.put("data", icds);

                response.setResponseMessage(messageBundle.getString("icd.versions.search.success"));
                response.setResponseCode(ResponseEnum.ICD_VERSIONS_FOUND_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("searchICDVersion fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("searchICDVersion failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @return Response with all search Filtered ICDs Version.
     * @author Jamal
     * @description API will return detail of all filtered ICDs Version.
     * @since 03-05-2017
     */
    @ApiOperation(httpMethod = "PUT", value = "Update ICDVersion  ",
            notes = "This method will return Status while Updating  ICDVersion",
            produces = "application/json", nickname = "Update  ICDVersion",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Update ICDVersion  successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/version/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateICDVersion(HttpServletRequest request,
                                              @RequestBody ICDVersionWrapper createRequest) {
        logger.info("updateICDVersion API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseData(null);
        response.setResponseMessage(messageBundle.getString("icd.version.update.error"));
        response.setResponseCode(ResponseEnum.ICD_VERSION_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());

        try {

            if (createRequest.getId() <= 0) {
                response.setResponseMessage(messageBundle.getString("icd.version.update.required"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateICDVersion API - version id not available.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (HISCoreUtil.isNull(createRequest.getName())) {
                response.setResponseMessage(messageBundle.getString("icd.version.update.name.required"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateICDVersion API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (icdService.isICDVersionNameAlreadyExistAgainstICDVersionNameId(createRequest.getName(), createRequest.getId())) {
                response.setResponseMessage(messageBundle.getString("icd.version.already.exist"));
                response.setResponseCode(ResponseEnum.ICD_CODE_ALREADY_EXIST_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            ICDVersion icdVersion = icdService.updateICDVersion(createRequest);
            if (HISCoreUtil.isValidObject(icdVersion)) {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("icd.version.update.success"));
                response.setResponseCode(ResponseEnum.ICD_VERSION_UPDATE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateICDCode Version failed.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "DELETE", value = "Delete ICD Version",
            notes = "This method will return Deleted Status of  ICD Version",
            produces = "application/json", nickname = "Delete  ICD Version ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted  ICD Version successful", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/version/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteICDVersion(HttpServletRequest request,
                                              @RequestParam("iCDVersionId") long iCDVersionId) {
        logger.info("deleteICDVersion Api Called..");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            if (iCDVersionId <= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("deleteICDVersion API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (this.icdService.isVersionAssociated(iCDVersionId)) {
                response.setResponseMessage(messageBundle.getString("icd.versions.delete.has.child"));
                response.setResponseCode(ResponseEnum.ICD_VERSION_HAS_CHILD.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("deleteICDVersion API - ICD Version has child record so record will not  delete.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (icdService.deletedICDVersion(iCDVersionId)) {
                response.setResponseMessage(messageBundle.getString("icd.version.delete.success"));
                response.setResponseCode(ResponseEnum.ICD_VERSION_DELETE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info("deleteICDVersion Successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("icd.already.deleted"));
            response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);
            logger.info("ICDs Already Deleted...");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("Delete deleteICDVersion failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "saveCodeVersion",
            notes = "This method will return Status while saving CodeVersion",
            produces = "application/json", nickname = "saveCodeVersion",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "saveCodeVersion  successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/codeVersion/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveCodeVersion(HttpServletRequest request,
                                             @RequestBody ICDCodeVersionWrapper createRequest) {
        logger.info("saveCodeVersion API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseData(null);
        response.setResponseMessage(messageBundle.getString("icd.code.version.save.error"));
        response.setResponseCode(ResponseEnum.ICD_CODE_VERSION_SAVE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());

        try {

            if (HISCoreUtil.isListEmpty(createRequest.getiCDCodes()) ||
                    !HISCoreUtil.isValidObject(createRequest.getSelectedICDVersionId())) {
                response.setResponseMessage(messageBundle.getString("icd.manage.list.empty"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("saveCodeVersion API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            List<ICDCodeVersion> icdCodeVersions = icdService.saveAssociateICDCVs(createRequest);
            if (HISCoreUtil.isValidObject(icdCodeVersions)) {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("icd.code.version.save.success"));
                response.setResponseCode(ResponseEnum.ICD_CODE_VERSION_SAVE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("saveCodeVersion failed.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "DELETE", value = "deleteCodeVersion",
            notes = "This method will return delete Code Version STATUS",
            produces = "application/json", nickname = "deleteCodeVersion",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "delete Code Version successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/codeVersion/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCodeVersion(HttpServletRequest request,
                                               @RequestParam("associateICDCVId") long associateICDCVId) {
        logger.info("deleteCodeVersion Api Called..");
        GenericAPIResponse response = new GenericAPIResponse();


        try {
            if (associateICDCVId <= 0) {
                response.setResponseMessage(messageBundle.getString("icd.version.required"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("deleteAssociateICDCV API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (icdService.deletedAssociateICDCV(associateICDCVId)) {
                response.setResponseMessage(messageBundle.getString("icd.code.version.delete.success"));
                response.setResponseCode(ResponseEnum.ICD_CODE_VERSION_DELETE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info("deleteAssociateICDCV Successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("icd.code.version.delete.already"));
            response.setResponseCode(ResponseEnum.ICD_CODE_VERSION_DELETE_ALREADY.getValue());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseData(null);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("deleteAssociateICDCV failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "DELETE", value = "get associated ICDCV",
            notes = "This method will return associated Status of   associate ICDCVs",
            produces = "application/json", nickname = "Associated ICDCVs ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get Associated ICDCVs successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/version/codes", method = RequestMethod.GET)
    public ResponseEntity<?> getCodesByVersion(HttpServletRequest request,
                                               @RequestParam("versionId") long versionId) {
        logger.info("getAssociatedICDCVAgainstICDVId Api Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.associated.not.found"));
        response.setResponseCode(ResponseEnum.ICD_CODE_DELETE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (versionId <= 0) {
                response.setResponseMessage(messageBundle.getString("icd.version.required"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getAssociatedICDCVAgainstICDVId API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            List<ICDCodeWrapper> codes = this.icdService.codes();
            List<ICDCodeWrapper> associatedCodes = this.icdService.getAssociatedICDCVByVId(versionId);//this.iCDService.getAssociatedICDCVByVId(versionId);
            String descriptionCodeVersion = "";
            for (ICDCodeWrapper associatedCodeWrapper : associatedCodes) {

                for (ICDCodeWrapper codeWrapper : codes) {
                    if (codeWrapper.getId() == associatedCodeWrapper.getId()) {
                        codeWrapper.setCheckedCode(true);
                        descriptionCodeVersion = associatedCodeWrapper.getDescriptionCodeVersion();
                        break;
                    }
                }
            }

            Map<String, Object> map = new HashMap<>();

            if (!HISCoreUtil.isListEmpty(codes)) {
                map.put("code", codes);
                map.put("des", descriptionCodeVersion);
                response.setResponseMessage(messageBundle.getString("icd.associated.found"));
                response.setResponseCode(ResponseEnum.ICD_ASSOCIATED_FOUND_SUCCESS.getValue());
                response.setResponseData(map);
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Associated found  Successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("Delete ICDVersion failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "DELETE", value = "get associated ICDCV",
            notes = "This method will return associated Status of   associate ICDCVs",
            produces = "application/json", nickname = "Associated ICDCVs ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get Associated ICDCVs successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/codes/associated", method = RequestMethod.GET)
    public ResponseEntity<?> getAssociatedCodesByVersionId(HttpServletRequest request,
                                                           @RequestParam("versionId") long versionId) {
        logger.info("getCodesByVersionId Api Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.associated.not.found"));
        response.setResponseCode(ResponseEnum.ICD_CODE_DELETE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (versionId <= 0) {
                response.setResponseMessage(messageBundle.getString("icd.version.required"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getCodesByVersionId API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            List<ICDCodeWrapper> associatedCodes = this.icdService.getAssociatedICDCVByVId(versionId);
            Map<String, Object> map = new HashMap<>();

            if (!HISCoreUtil.isListEmpty(associatedCodes)) {
                map.put("code", associatedCodes);
                response.setResponseMessage(messageBundle.getString("icd.associated.found"));
                response.setResponseCode(ResponseEnum.ICD_ASSOCIATED_FOUND_SUCCESS.getValue());
                response.setResponseData(map);
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Associated found  Successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("Delete ICDVersion failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "DELETE", value = "get associated ICDV",
            notes = "This method will return associated ICDVs",
            produces = "application/json", nickname = "Associated ICDVs ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get Associated ICDVs successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/versions/associated", method = RequestMethod.GET)
    public ResponseEntity<?> getAssociatedVersionsByCodeId(HttpServletRequest request,
                                                           @RequestParam("codeId") long codeId) {
        logger.info("getAssociatedVersionsByCodeId Api Called..");
        GenericAPIResponse response = new GenericAPIResponse();


        try {
            if (codeId <= 0) {
                response.setResponseMessage(messageBundle.getString("icd.code.required"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getAssociatedVersionsByCodeId API - Code id not present. Please provide code id.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            List<ICDVersionWrapper> associatedVersions = this.icdService.getAssociatedICDVByCId(codeId);

            if (!HISCoreUtil.isListEmpty(associatedVersions)) {
                response.setResponseMessage(messageBundle.getString("icd.versions.code.fetched.success"));
                response.setResponseCode(ResponseEnum.ICD_VERSIONS_BY_CODE_SUCCESS.getValue());
                response.setResponseData(associatedVersions);
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Associated found  Successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("icd.versions.code.fetched.not.found"));
            response.setResponseCode(ResponseEnum.ICD_CODE_DELETE_ERROR.getValue());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseData(null);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("getAssociatedVersionsByCodeId Exception.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return Response with all search Filtered Code Version by Version.
     * @author Jamal
     * @description API will get data from  icd_code_version table by Version Name.
     * @since 09-05-2017
     */
    @ApiOperation(httpMethod = "GET", value = "Search Code Version by Version",
            notes = "This method will return Searched Code Version by Version",
            produces = "application/json", nickname = "Get Searched Code Version by Version ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Searched Code Version by Version fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/codeVersion/search/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> searchCodeVersionByParam(HttpServletRequest request,
                                                      @PathVariable("page") int page,
                                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                      @RequestParam(value = "versionName") String versionName,
                                                      @RequestParam(value = "searchCodeVersionCode") String searchCode) {

        logger.info("get all Searched  Code Version By Version Name");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.not-found"));
        response.setResponseCode(ResponseEnum.ICD_CODE_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {

            List<ICDCodeVersionWrapper> searchedCodeVersions = new ArrayList<>();
            int icdCount = 0;
            Pageable pageable = new PageRequest(page, pageSize);
            if (!versionName.isEmpty() && !searchCode.isEmpty()) {
                searchedCodeVersions = icdService.searchCodeVersionByCodeAndVersionName(pageable, versionName, searchCode);
                icdCount = icdService.countSearchCodeVersionByCodeAndVersionName(versionName, searchCode);
            } else if (!versionName.isEmpty()) {
                searchedCodeVersions = icdService.searchCodeVersionByVersionName(pageable, versionName);
                icdCount = icdService.countSearchCodeVersionByVersionName(versionName);
            } else if (!searchCode.isEmpty()) {
                searchedCodeVersions = icdService.searchCodeVersionByCode(pageable, searchCode);
                icdCount = icdService.countSearchCodeVersionByCode(searchCode);
            } else {
                searchedCodeVersions = icdService.codeVersions(page, pageSize);
                icdCount = icdService.countCodeVersions();
            }


//            int icdCount = icdService.countCodeVersionByCodeAndVersionName(versionName, searchCode);

            if (!HISCoreUtil.isListEmpty(searchedCodeVersions)) {
                logger.info("search Code Version By VersionName fetched from DB successfully...");
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (icdCount > pageSize) {
                    int remainder = icdCount % pageSize;
                    int totalPages = icdCount / pageSize;
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
                returnValues.put("data", searchedCodeVersions);

                response.setResponseMessage(messageBundle.getString("icd.fetched.success"));
                response.setResponseCode(ResponseEnum.ICD_CODE_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("All search Code Version By VersionName fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("search Code Version By VersionName failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "get associated ICDCV",
            notes = "This method will return associated Status of   associate ICDCVs",
            produces = "application/json", nickname = "Associated ICDCVs ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get Associated ICDCVs successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/code/get", method = RequestMethod.GET)
    public ResponseEntity<?> getCodeById(HttpServletRequest request,
                                         @RequestParam("codeId") long codeId) {
        logger.info("getCodeById Api Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.associated.not.found"));
        response.setResponseCode(ResponseEnum.ICD_CODE_DELETE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (codeId <= 0) {
                response.setResponseMessage(messageBundle.getString("icd.code.required"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getCodeById API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            List<ICDVersionWrapper> versions = this.icdService.versios();
            ICDCodeWrapper codeWrapper = this.icdService.getCodeById(codeId);//this.iCDService.getAssociatedICDCVByVId(versionId);

            for (ICDVersionWrapper selectedVersionWrap : codeWrapper.getSelectedVersions()) {
                for (ICDVersionWrapper version : versions) {
                    if (selectedVersionWrap.getId() == version.getId()) {
                        version.setSelectedVersion(true);
                    }
                }
            }

            codeWrapper.setSelectedVersions(versions);
            response.setResponseMessage(messageBundle.getString("icd.code.found.success"));
            response.setResponseCode(ResponseEnum.ICD_CODE_FOUND.getValue());
            response.setResponseData(codeWrapper);
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Code found  Successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("getCodeById exception.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Import ICD Data",
            notes = "This method will import icd data",
            produces = "application/json", nickname = "Import ICD",
            response = GenericAPIResponse.class, protocols = "https")
    @RequestMapping(value = "/importRecords", method = RequestMethod.POST)
    public ResponseEntity<?> importICDcodeAndVersion(@RequestParam("dataFile") MultipartFile dataFile ) {

        logger.info("importICD codes and versions API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            String fileName = dataFile.getOriginalFilename();
            File file = HISCoreUtil.multipartToFile(dataFile);
//            int records = drugService.readExcel( dataFile );
            int records = bulkImportService.importIcdCodeRecords(fileName);
            if (records > 0) {
                response.setResponseMessage(messageBundle.getString("icd.code.records.import.success"));
            } else {
                response.setResponseMessage(messageBundle.getString("icd.code.no.record.import"));
            }
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info(records + " - Appointment records imported successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (FileNotFoundException fnfe) {
            logger.error("importICD codes and versions File not found.", fnfe.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("icd.code.import.file.not.found"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            logger.error("importICD codes and versions Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("icd.code.records.import.failed"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
