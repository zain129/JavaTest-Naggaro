package com.zainimtiaz.nagarro.controller.setting;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.service.InsurranceService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.sd.his.wrapper.*;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.InsuranceProfileWrapper;
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


@RequestMapping("/setting/Insurrance")
@RestController
public class InsurranceAPI {


    @Autowired
    private InsurranceService issService;

    private final Logger logger = LoggerFactory.getLogger(InsurranceAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "GET", value = "Insurance Plan",
            notes = "This method will return   Insurance Plan ",
            produces = "application/json", nickname = " versions",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = " fetched", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/Insurance/Plan", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPlan(HttpServletRequest request) {

        logger.info("Plan API initiated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.versions.not.found"));
        response.setResponseCode(ResponseEnum.ICD_VERSION_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ICD_VERSION_ERROR.getValue());
        response.setResponseData(null);
        try {

            logger.info("Found Successfully");
            response.setResponseData(issService.getPlans());
            response.setResponseMessage(messageBundle.getString("icd.versions.found.success"));
            response.setResponseCode(ResponseEnum.ICD_VERSIONS_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error(" failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "GET", value = " Profile ",
            notes = "This method will return codes Not Deleted",
            produces = "application/json", nickname = "codes Not Deleted",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "codes Not Deleted fetched", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/InsuranceProfile", method = RequestMethod.GET)
    public ResponseEntity<?> getAllInsuranceProfile(HttpServletRequest request) {

        logger.info(" API initiated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.codes.not.found"));
        response.setResponseCode(ResponseEnum.ICD_VERSION_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {

            response.setResponseData(issService.getProfiles());
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
    @RequestMapping(value = "/plan/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveInsurrancePlan(HttpServletRequest request,
                                         @RequestBody InsuranceProfileWrapper createRequest) {
        logger.info("save API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseData(null);
        response.setResponseMessage(messageBundle.getString("icd.code.save.error"));
        response.setResponseCode(ResponseEnum.ICD_CODE_SAVE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());

        try {

            if (HISCoreUtil.isNull(createRequest.getName())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("save API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (issService.isNameAlreadyExist(createRequest.getName())) {
                response.setResponseMessage(messageBundle.getString("plan.code.already.exist"));
                response.setResponseCode(ResponseEnum.PLAN_ALREADY_EXIST_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            String message = issService.saveplan(createRequest);
            if (HISCoreUtil.isValidObject(message)) {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("plan.code.save.success"));
                response.setResponseCode(ResponseEnum.PLAN_SAVE_SUCCESS.getValue());
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
    @RequestMapping(value = "/profile/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveInsurranceProfile(HttpServletRequest request,
                                                @RequestBody InsuranceProfileWrapper createRequest) {
        logger.info("save API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseData(null);
        response.setResponseMessage(messageBundle.getString("icd.code.save.error"));
        response.setResponseCode(ResponseEnum.ICD_CODE_SAVE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());

        try {

            if (HISCoreUtil.isNull(createRequest.getName())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("save API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (issService.isProfileNameAlreadyExist(createRequest.getName())) {
                response.setResponseMessage(messageBundle.getString("profile.already.exist"));
                response.setResponseCode(ResponseEnum.PROFILE_ALREADY_EXIST_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            String message = issService.saveProfile(createRequest);
            if (HISCoreUtil.isValidObject(message)) {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("profile.save.success"));
                response.setResponseCode(ResponseEnum.PROFILE_SAVE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(" API - saving failed.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "PUT", value = "Update   ",
            notes = "This method will return Status ",
            produces = "application/json", nickname = "Update  ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Update   successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/plan/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePlan(HttpServletRequest request,
                                           @RequestBody InsuranceProfileWrapper createRequest) {
        logger.info(" API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseData(null);
        response.setResponseMessage(messageBundle.getString("plan.code.update.error"));
        response.setResponseCode(ResponseEnum.PLAN_UPDATE_ERROR.getValue());
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
            if (HISCoreUtil.isNull(createRequest.getName())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateICDCode API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            issService.updatePlan(createRequest);
            response.setResponseData(null);
            response.setResponseMessage(messageBundle.getString("plan.update.success"));
            response.setResponseCode(ResponseEnum.PLAN_UPDATE_SUCC.getValue());
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


    @ApiOperation(httpMethod = "PUT", value = "Update   ",
            notes = "This method will return Status ",
            produces = "application/json", nickname = "Update  ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Update   successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/profile/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProfile(HttpServletRequest request,
                                        @RequestBody InsuranceProfileWrapper createRequest) {
        logger.info(" API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseData(null);
        response.setResponseMessage(messageBundle.getString("profile.code.update.error"));
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
            if (HISCoreUtil.isNull(createRequest.getName())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateICDCode API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            /*if (issService.isPlanAlreadyExistAgainstId(createRequest.getId())) {
                response.setResponseMessage(messageBundle.getString("icd.code.already.exist"));
                response.setResponseCode(ResponseEnum.ICD_CODE_ALREADY_EXIST_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }*/
            issService.updateProfile(createRequest);
            response.setResponseData(null);
            response.setResponseMessage(messageBundle.getString("profile.update.success"));
            response.setResponseCode(ResponseEnum.PROFILE_UPDATE_SUCC.getValue());
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


    @ApiOperation(httpMethod = "DELETE", value = "Delete ",
            notes = "This method will return Deleted Status of ",
            produces = "application/json", nickname = "Delete  ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted  successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/code/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePlan(HttpServletRequest request,
                                           @RequestParam("Id") Long Id) {
        logger.info("Delete  Api Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("plan.delete.error"));
        response.setResponseCode(ResponseEnum.ICD_CODE_DELETE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (Id <= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("deleteICD API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (this.issService.isCodeAssociated(Id)) {
                response.setResponseMessage(messageBundle.getString("plan.code.delete.has.child"));
                response.setResponseCode(ResponseEnum.ICD_CODE_HAS_CHILD.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
              //  logger.error("deleteICD API - ICD CODE has child record so record will not delete. First delete child record then you can delete it");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (issService.deletedPlan(Id)) {
                response.setResponseMessage(messageBundle.getString("plan.delete.success"));
                response.setResponseCode(ResponseEnum.PLAN_DELETE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
            //    logger.info("ICDs Deleted Successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseMessage(messageBundle.getString("plan.already.deleted"));
                response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
             //   logger.info("ICDs Already Deleted...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
        //    logger.error("Delete ICDCode failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "DELETE", value = "Delete ",
            notes = "This method will return Deleted Status of ",
            produces = "application/json", nickname = "Delete  ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted  successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/profile/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProfile(HttpServletRequest request,
                                        @RequestParam("Id") Long Id) {
        logger.info("Delete  Api Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("profile.delete.error"));
        response.setResponseCode(ResponseEnum.ICD_CODE_DELETE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (Id <= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
             //   logger.error("deleteICD API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (this.issService.isCodeAssociatedProfile(Id)) {
                response.setResponseMessage(messageBundle.getString("icd.code.delete.has.child"));
                response.setResponseCode(ResponseEnum.ICD_CODE_HAS_CHILD.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
             //   logger.error("deleteICD API - ICD CODE has child record so record will not delete. First delete child record then you can delete it");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (this.issService.deletedProfile(Id)) {
                response.setResponseMessage(messageBundle.getString("profile.delete.success"));
                response.setResponseCode(ResponseEnum.PROFILE_DELETE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info(" Deleted Successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseMessage(messageBundle.getString("profile.already.deleted"));
                response.setResponseCode(ResponseEnum.NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("ICDs Already Deleted...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Delete  failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }










}
