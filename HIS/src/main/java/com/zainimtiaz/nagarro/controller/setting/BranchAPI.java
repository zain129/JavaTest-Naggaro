package com.zainimtiaz.nagarro.controller.setting;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.Branch;
import com.zainimtiaz.nagarro.model.Doctor;
import com.zainimtiaz.nagarro.model.Organization;
import com.zainimtiaz.nagarro.repository.OrganizationRepository;
import com.zainimtiaz.nagarro.service.BranchService;
import com.zainimtiaz.nagarro.service.OrganizationService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.sd.his.wrapper.*;
import com.zainimtiaz.nagarro.wrapper.request.BranchRequestWrapper;
import com.zainimtiaz.nagarro.wrapper.response.BranchResponseWrapper;
import com.zainimtiaz.nagarro.model.Room;
import com.zainimtiaz.nagarro.wrapper.*;
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
 * @author    : waqas kamran
 * @Date      : 17-Apr-18
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
 * @FileName  : BranchAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@RestController
@RequestMapping("/setting/branch")
public class BranchAPI {

    @Autowired
    private BranchService branchService;
    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationService organizationService;

    private final Logger logger = LoggerFactory.getLogger(BranchAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "GET", value = "All Branches",
            notes = "This method will return all Branches",
            produces = "application/json", nickname = "All Branches",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Branches fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllBranches(HttpServletRequest request) {

        logger.info("getAllBranches API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("branch.fetch.error"));
        response.setResponseCode(ResponseEnum.BRANCH_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getAllBranches API - branches fetching from DB");
            List<BranchResponseWrapper> branches = branchService.getAllActiveBranches();
            if (HISCoreUtil.isListEmpty(branches)) {
                response.setResponseMessage(messageBundle.getString("branch.not-found"));
                response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("getAllBranches API - Branches not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("branch.fetch.success"));
            response.setResponseCode(ResponseEnum.BRANCH_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(branches);

            logger.info("getAllBranches API - Branches successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllBranches API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "All Branches",
            notes = "This method will return all Branches",
            produces = "application/json", nickname = "All Branches",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Branches fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/all/all", method = RequestMethod.GET)
    public ResponseEntity<?> getBranches() {

        logger.info("getBranches API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("branch.fetch.error"));
        response.setResponseCode(ResponseEnum.BRANCH_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getBranches API - branches fetching from DB");
            List<BranchWrapperPart> branches = branchService.getActiveBranches();
            if (HISCoreUtil.isListEmpty(branches)) {
                response.setResponseMessage(messageBundle.getString("branch.not-found"));
                response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("getBranches API - Branches not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("branch.fetch.success"));
            response.setResponseCode(ResponseEnum.BRANCH_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(branches);

            logger.info("getBranches API - Branches successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getBranches API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "POST", value = "Create Branch",
            notes = "This method will Create Branch",
            produces = "application/json", nickname = "Create Branch",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Branch successfully created", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createBranch(HttpServletRequest request,
                                          @RequestBody BranchRequestWrapper branchRequestWrapper) {
        logger.info("Create Branch API called...");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("branch.add.error"));
        response.setResponseCode(ResponseEnum.BRANCH_ADD_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            Branch alreadyExist = branchService.findByBranchName(branchRequestWrapper.getBranchName());
            if (HISCoreUtil.isValidObject(alreadyExist)) {
                response.setResponseMessage(messageBundle.getString("branch.add.already-found.error"));
                response.setResponseCode(ResponseEnum.BRANCH_ALREADY_EXIST_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Branch already exist with the same name...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }



            Branch savedBranch = branchService.saveBranch(branchRequestWrapper);
            if (HISCoreUtil.isValidObject(savedBranch)) {
                response.setResponseData(savedBranch);
                response.setResponseMessage(messageBundle.getString("branch.add.success"));
                response.setResponseCode(ResponseEnum.BRANCH_ADD_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Branch created successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception ex) {
            logger.error("Branch Creation Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Paginated Branches",
            notes = "This method will return Paginated Branches",
            produces = "application/json", nickname = "Get Paginated Branches ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated Branches fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaginatedBranches(HttpServletRequest request,
                                                     @PathVariable("page") int page,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllBranch paginated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("branch.not.found"));
        response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<BranchResponseWrapper> branchWrappers = branchService.findAllBranches(page, pageSize);
            int countBranch = branchService.totalBranches();
            if (!HISCoreUtil.isListEmpty(branchWrappers)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countBranch > pageSize) {
                    int remainder = countBranch % pageSize;
                    int totalPages = countBranch / pageSize;
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

                response.setResponseMessage(messageBundle.getString("branch.fetched.success"));
                response.setResponseCode(ResponseEnum.BRANCH_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAllPaginatedBranch Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated Branches failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(httpMethod = "GET", value = "All Doctors With Branches",
            notes = "This method will return all Branches Associated With Branch",
            produces = "application/json", nickname = "All Branches With Doctors",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Branches fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/doctorsInBranch/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllBranchesWithDoctors(HttpServletRequest request, @PathVariable("id") long id) {

        logger.error("getAllBranches With Doctor API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("branch.fetch.error"));
        response.setResponseCode(ResponseEnum.BRANCH_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getAllBranches API - branches fetching from DB");
            List<Doctor> branchesWithDoctors = branchService.getDoctorsWithBranch(id);
            if (HISCoreUtil.isListEmpty(branchesWithDoctors)) {
                response.setResponseMessage(messageBundle.getString("branch.not-found"));
                response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getAllBranches API - Branches not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("branch.fetch.success"));
            response.setResponseCode(ResponseEnum.BRANCH_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(branchesWithDoctors);

            logger.error("getAllBranches API - Branches successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllBranches API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Organization",
            notes = "This method will return  Organization Associated With Branch",
            produces = "application/json", nickname = "Organization ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Organization fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/organization", method = RequestMethod.GET)
    public ResponseEntity<?> getOrganization(HttpServletRequest request) {

        logger.error("get Organization");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("branch.fetch.error"));
        response.setResponseCode(ResponseEnum.BRANCH_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("get Organization API ");
            Organization organization = organizationRepository.findOne(1L);
            if (!HISCoreUtil.isValidObject(organization)) {
                response.setResponseMessage(messageBundle.getString("branch.not-found"));
                response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getAllBranches API - Branches not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setResponseMessage(messageBundle.getString("branch.fetch.success"));
            response.setResponseCode(ResponseEnum.BRANCH_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(organization);

            logger.error("get Organization API .");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get Organization API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete Branch",
            notes = "This method will Delete Branch on base of id",
            produces = "application/json", nickname = "Delete Branch ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Branch Deleted successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBranch(HttpServletRequest request,
                                          @PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("branch.delete.error"));
        response.setResponseCode(ResponseEnum.BRANCH_NOT_DELETED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            Branch dbBranch = this.branchService.findBranchById(id);

            if (HISCoreUtil.isValidObject(dbBranch)) {
                Branch branch = branchService.deleteBranch(dbBranch);
                if(HISCoreUtil.isValidObject(branch)){
                    response.setResponseData(null);
                    response.setResponseMessage(messageBundle.getString("branch.delete.success"));
                    response.setResponseCode(ResponseEnum.BRANCH_DELETED_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("Branch Deleted successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);}
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("branch.not.found"));
                response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("branch.not.found"));
                response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());

                logger.info("Unable to find Branch...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Unable to delete Branch.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "PUT", value = "Update Branch ",
            notes = "This method will Update Branch",
            produces = "application/json", nickname = "Update Branch",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Branch successfully updated", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateBranch(HttpServletRequest request,
                                          @PathVariable("id") long id,
                                          @RequestBody BranchRequestWrapper branchRequestWrapper) {

        logger.info("update Branch API called..." + branchRequestWrapper.getBranchName());

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("branch.update.error"));
        response.setResponseCode(ResponseEnum.BRANCH_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {

            Branch alreadyExistBranch = branchService.findBranchById(id);
            if (HISCoreUtil.isValidObject(alreadyExistBranch)) {
                logger.info("Branch founded...");

                if (branchService.isBranchNameOrIdExistsAlready(branchRequestWrapper.getBranchName(),id)) {
                    response.setResponseMessage(messageBundle.getString("branch.add.already-found.error"));
                    response.setResponseCode(ResponseEnum.BRANCH_ALREADY_EXIST_ERROR.getValue());
                    response.setResponseStatus(ResponseEnum.ERROR.getValue());
                    response.setResponseData(null);
                    logger.error("Branch already exist with the same name...");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                Branch branchUpdated = branchService.updateBranch(branchRequestWrapper, alreadyExistBranch);
                if (HISCoreUtil.isValidObject(branchUpdated)) {
                    logger.info("Branch Updated...");
                    response.setResponseData(branchUpdated);
                    response.setResponseMessage(messageBundle.getString("branch.update.success"));
                    response.setResponseCode(ResponseEnum.BRANCH_UPDATE_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("Branch updated successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                logger.info("Branch not found...");
                response.setResponseMessage(messageBundle.getString("branch.not.found"));
                response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Branch not updated...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception ex) {
            logger.error("Update Branch Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ApiOperation(httpMethod = "GET", value = "Fetch Branch",
            notes = "This method will return Branch on base of id",
            produces = "application/json", nickname = "Get Single Branch",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Branch found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getBranchById(HttpServletRequest request,
                                           @PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("branch.not.found"));
        response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            BranchResponseWrapper dbBranch = this.branchService.getById(id);

            if (HISCoreUtil.isValidObject(dbBranch)) {
                response.setResponseData(dbBranch);
                response.setResponseCode(ResponseEnum.BRANCH_FOUND.getValue());
                response.setResponseMessage(messageBundle.getString("branch.found"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Branch Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("branch.not.found"));
                response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("Branch Not Found ...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Branch Not Found", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*  @ApiOperation(httpMethod = "GET", value = "Branches Name",
              notes = "This method will return  Branches name",
              produces = "application/json", nickname = "Get all Branches",
              response = GenericAPIResponse.class, protocols = "https")
      @ApiResponses({
              @ApiResponse(code = 200, message = " Branches name fetched successfully", response = GenericAPIResponse.class),
              @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
              @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
              @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
              @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
      @RequestMapping(value = "/name", method = RequestMethod.GET)
      public ResponseEntity<?> getAllBranchesName(HttpServletRequest request) {
          logger.info("getAllBranch Name..");

          GenericAPIResponse response = new GenericAPIResponse();
          response.setResponseMessage(messageBundle.getString("branch.not.found"));
          response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
          response.setResponseStatus(ResponseEnum.ERROR.getValue());
          response.setResponseData(null);

          try {
              List<String> branchesName = branchService.findAllBranchName();
              if (!HISCoreUtil.isListEmpty(branchesName)) {

                  response.setResponseMessage(messageBundle.getaString("branch.fetched.success"));
                  response.setResponseCode(ResponseEnum.BRANCH_FOUND.getValue());
                  response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                  response.setResponseData(branchesName);
                  logger.info("All Branches Fetched successfully...");
                  return new ResponseEntity<>(response, HttpStatus.OK);
              }
              return new ResponseEntity<>(response, HttpStatus.OK);
          } catch (Exception ex) {
              logger.error("get all Branches failed.", ex.fillInStackTrace());
              response.setResponseData("");
              response.setResponseStatus(ResponseEnum.ERROR.getValue());
              response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
              response.setResponseMessage(messageBundle.getString("exception.occurs"));

              return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }
  */
    @ApiOperation(httpMethod = "GET", value = "Search Branch",
            notes = "This method will return Branch on base of search",
            produces = "application/json", nickname = "Search Branch",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Branch found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> searchPaginatedBranches(HttpServletRequest request,
                                                     @PathVariable("page") int page,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                     @RequestParam(value = "branch") Long branchName){


        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("branch.not.found"));
        response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<BranchResponseWrapper> branchWrappers = branchService.searchByBranchNameAndDepartment(branchName,page, pageSize);

            int countBranch = branchService.totalBranches();

            if (!HISCoreUtil.isListEmpty(branchWrappers)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countBranch > pageSize) {
                    int remainder = countBranch % pageSize;
                    int totalPages = countBranch / pageSize;
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

                response.setResponseMessage(messageBundle.getString("branch.fetched.success"));
                response.setResponseCode(ResponseEnum.BRANCH_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("searched Branch Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("searched Branch failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "All Branches With Doctor ",
            notes = "This method will return all Branches",
            produces = "application/json", nickname = "All Branches",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Branches with Doctor and Services fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/branchdoctors", method = RequestMethod.GET)
    public ResponseEntity<?> getAllBranchesWithDoctorsAndServices(HttpServletRequest request) {

        logger.error("getAllBranches With Doctors API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("branch.fetch.error"));
        response.setResponseCode(ResponseEnum.BRANCH_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getAllBranches API - branches fetching from DB");
            Set<BranchResponseWrapper> branches = branchService.getAllActiveBranchesWithDoctors();
            if (HISCoreUtil.isSetEmpty(branches)) {
                response.setResponseMessage(messageBundle.getString("branch.not-found"));
                response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getAllBranches API - Branches not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("branch.fetch.success"));
            response.setResponseCode(ResponseEnum.BRANCH_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(branches);

            logger.error("getAllBranches API - Branches successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllBranches API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Rooms count of Branches",
            notes = "This method will return Rooms count of Branches",
            produces = "application/json", nickname = "Get Rooms count of Branches ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Rooms of Branches fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/rooms/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getNumberOfRooms(HttpServletRequest request, @PathVariable("id") Long branchId) {
        logger.info("getNumberOfRooms called..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("rooms.configuration.fetch.error"));
        response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            List<Room> branchRooms = branchService.getTotalRoomsByBrId(branchId);
            int roomCount = branchRooms.size();

            Map<String, Object> returnValues = new LinkedHashMap<>();
            returnValues.put("data", roomCount);
            response.setResponseMessage(messageBundle.getString("rooms.configuration.fetched.success"));
            response.setResponseCode(ResponseEnum.BRANCH_FOUND.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(returnValues);

            logger.info("getNumberOfRooms Fetched successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getNumberOfRooms failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "List of Countries",
            notes = "This method will return all countries",
            produces = "application/json", nickname = "Get List of Countries ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of Counties fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/countries/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCountries(HttpServletRequest request) {
        logger.info("getAllCountries called..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("country.fetch.error"));
        response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {

            List<CountryWrapper> countryWrappers = branchService.getAllCountries();
            Map<String, Object> returnValues = new LinkedHashMap<>();
            returnValues.put("data", countryWrappers);
            response.setResponseMessage(messageBundle.getString("country.fetched.success"));
            response.setResponseCode(ResponseEnum.BRANCH_FOUND.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(returnValues);

            logger.info("getAllCountries Fetched successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllCountries failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "List of States",
            notes = "This method will return all States of a specific country",
            produces = "application/json", nickname = "Get List of States ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of States fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/states/{countryId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllStates(HttpServletRequest request, @PathVariable("countryId") Long countryId) {
        logger.info("getAllStates called..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("city.fetch.error"));
        response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            List<?> states = branchService.getStatesByCntryId(countryId);

            Map<String, Object> returnValues = new LinkedHashMap<>();
            returnValues.put("data", states);
            response.setResponseMessage(messageBundle.getString("city.fetched.success"));
            response.setResponseCode(ResponseEnum.BRANCH_FOUND.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(returnValues);

            logger.info("getAllStates Fetched successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllStates failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "List of Cities",
            notes = "This method will return all Cities of a specific state",
            produces = "application/json", nickname = "Get List of Cities ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of Cities fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/cities/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCities(HttpServletRequest request, @PathVariable("id") Long stateId) {
        logger.info("getAllCities called..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("city.fetch.error"));
        response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            List<?> cities = branchService.getCitiesByStateId(stateId);

            Map<String, Object> returnValues = new LinkedHashMap<>();
            returnValues.put("data", cities);
            response.setResponseMessage(messageBundle.getString("city.fetched.success"));
            response.setResponseCode(ResponseEnum.BRANCH_FOUND.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(returnValues);

            logger.info("getAllCities Fetched successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllCities failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Branch's City State Country",
            notes = "This method will return city state country of branch",
            produces = "application/json", nickname = "Get Branch's City State Country ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Branch's City State Country fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/cityStateCountry/{branchId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCityStateCountryByBrId(HttpServletRequest request, @PathVariable("branchId") Long branchId) {
        logger.info("getCountryById called..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("rooms.configuration.fetch.error"));
        response.setResponseCode(ResponseEnum.BRANCH_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {

            CityWrapper city = branchService.getCityByBrId(branchId);
            StateWrapper state = branchService.getStateByBrId(branchId);
            CountryWrapper country = branchService.getCountryByBrId(branchId);

            Map<String, Object> cityStateCountry = new HashMap<>();
            cityStateCountry.put("city", city);
            cityStateCountry.put("state", state);
            cityStateCountry.put("country", country);

            Map<String, Object> returnValues = new LinkedHashMap<>();
            returnValues.put("data", cityStateCountry);
            response.setResponseMessage(messageBundle.getString("branch.data.fetch.success"));
            response.setResponseCode(ResponseEnum.BRANCH_FOUND.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(returnValues);

            logger.info("getAllCountries Fetched successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllCountries failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}