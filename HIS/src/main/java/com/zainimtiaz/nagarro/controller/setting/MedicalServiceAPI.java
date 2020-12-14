package com.zainimtiaz.nagarro.controller.setting;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.MedicalService;
import com.zainimtiaz.nagarro.model.Organization;
import com.zainimtiaz.nagarro.service.BranchService;
import com.zainimtiaz.nagarro.service.DepartmentService;
import com.zainimtiaz.nagarro.service.MedicalServicesService;
import com.zainimtiaz.nagarro.service.OrganizationService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.sd.his.wrapper.*;
import com.zainimtiaz.nagarro.wrapper.BranchWrapperPart;
import com.zainimtiaz.nagarro.wrapper.DepartmentWrapper;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.MedicalServiceWrapper;
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
import java.util.*;
import java.util.stream.IntStream;

/*
 * @author    : Qari Muhammad Jamal
 * @Date      : 31-July-18
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
 * @FileName  : ${FILE_NAME}
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@RestController
@RequestMapping("/setting/medicalService")
public class MedicalServiceAPI {

    Logger logger = LoggerFactory.getLogger(MedicalServiceAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private MedicalServicesService medicalServicesService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private OrganizationService organizationService;

    @ApiOperation(httpMethod = "GET", value = "Paginated Medical Services",
            notes = "This method will return Paginated Medical Services",
            produces = "application/json", nickname = "Paginated CMedical Services",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated Medical Services fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaginatedAllMedicalServices(HttpServletRequest request,
                                                            @PathVariable("page") int page,
                                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        logger.error("getPaginatedAllMedicalServices API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("med.service.fetch.error"));
        response.setResponseCode(ResponseEnum.MED_SERVICE_FETCH_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getPaginatedAllMedicalServices - Medical Services fetching from DB");
            List<MedicalServiceWrapper> mss = medicalServicesService.findAllPaginatedMedicalServices(page, pageSize);
            int mssCount = medicalServicesService.countAllMedicalServices();
            logger.error("getPaginatedAllMedicalServices - Medical Services fetched successfully");

            if (!HISCoreUtil.isListEmpty(mss)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (mssCount > pageSize) {
                    int remainder = mssCount % pageSize;
                    int totalPages = mssCount / pageSize;
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
                returnValues.put("data", mss);

                response.setResponseMessage(messageBundle.getString("med.service.fetch.success"));
                response.setResponseCode(ResponseEnum.MED_SERVICE_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getPaginatedAllMedicalServices API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getPaginatedAllMedicalServices exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Paginated Medical Services",
            notes = "This method will return Paginated Medical Services",
            produces = "application/json", nickname = "Paginated CMedical Services",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated Medical Services fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllMedicalServices(HttpServletRequest request) {

        logger.error("getAllMedicalServices API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            logger.error("getAllMedicalServices - Medical Services fetching from DB");
            List<MedicalServiceWrapper> msLstW = new ArrayList<MedicalServiceWrapper>();
            List<MedicalServiceWrapper> mss = medicalServicesService.findAllMedicalServicesForDataTable();
            Organization dbOrganization = organizationService.getAllOrgizationData();
       //     String Zone = dbOrganization.getZone().getName().replaceAll("\\s", "");
            String systemCurrency = dbOrganization.getCurrencyFormat();
         //   String hoursFormat = dbOrganization.getHoursFormat();
        //    String dateFormat = dbOrganization.getDateFormat();
         //   String timeFormat = dbOrganization.getTimeFormat();
            for(int i=0;i<mss.size();i++){
                MedicalServiceWrapper ms=new MedicalServiceWrapper(mss.get(i));
                if (systemCurrency != null && (!systemCurrency.equals(""))) {
                    ms.setStrFee((medicalServicesService.formatCurrencyDisplay((ms.getFee()), systemCurrency)));
                    ms.setStrCost(medicalServicesService.formatCurrencyDisplay((ms.getCost()), systemCurrency));
                    mss.get(i).setStrCost(ms.getStrCost());
                    mss.get(i).setStrFee(ms.getStrFee());
               //     msLstW.add(ms);
                }else{
                    mss.get(i).setStrCost(String.valueOf(ms.getFee()));
                    mss.get(i).setStrFee(String.valueOf(ms.getCost()));
                }
            }
            logger.info("getAllMedicalServices - Medical Services fetched successfully" + mss.size());
            response.setResponseMessage(messageBundle.getString("med.service.fetch.success"));
            response.setResponseCode(ResponseEnum.MED_SERVICE_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(mss);

            logger.error("getAllMedicalServices API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("getAllMedicalServices exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(httpMethod = "GET", value = "all Branches by Medical Service id only selected",
            notes = "This method will return Branches by Medical Service Id, only selected",
            produces = "application/json", nickname = "Paginated CMedical Services",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "all Branches by Medical Service id only selected fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/departments/{msId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDepartmentsByMedicalServiceId(HttpServletRequest request,
                                                              @PathVariable("msId") long msId) {

        logger.error("getDepartmentsByMedicalServiceId API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            logger.error("getAllMedicalServices -  fetching from DB");
            List<DepartmentWrapper> mss = medicalServicesService.getCheckedDepartsByMedicalServiceId(msId);

            if (mss.size() > 0) {
                logger.info("getDepartmentsByMedicalServiceId -  fetched successfully" + mss.size());
                response.setResponseMessage(messageBundle.getString("med.service.fetch.success"));
                response.setResponseCode(ResponseEnum.MED_SERVICE_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            }
            response.setResponseData(mss);

            logger.info("getAllMedicalServices API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("getDepartmentsByMedicalServiceId exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(httpMethod = "GET", value = "all Branches by Medical Service id only selected",
            notes = "This method will return Branches by Medical Service Id, only selected",
            produces = "application/json", nickname = "Paginated CMedical Services",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "all Branches by Medical Service id only selected fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/branches/{msId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCheckedBranchesByMedicalServiceId(HttpServletRequest request,
                                                                  @PathVariable("msId") long msId) {

        logger.info("getCheckedBranchesByMedicalServiceId API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            logger.error("getCheckedBranchesByMedicalServiceId - fetching from DB");
            List<BranchWrapperPart> checkedBranches = medicalServicesService.getCheckedBranchesByMedicalServiceId(msId);

            if (checkedBranches.size() > 0) {

                logger.info("getCheckedBranchesByMedicalServiceId - Medical Services fetched successfully" + checkedBranches.size());
                response.setResponseMessage(messageBundle.getString("med.service.fetch.success"));
                response.setResponseCode(ResponseEnum.MED_SERVICE_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            }
            response.setResponseData(checkedBranches);

            logger.info("getCheckedBranchesByMedicalServiceId API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("getCheckedBranchesByMedicalServiceId exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(httpMethod = "GET", value = "Department Medical Services",
            notes = "This method will return Paginated Medical Services",
            produces = "application/json", nickname = "Paginated CMedical Services",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Department Medical Services fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getDeptMedicalService/{deptId}", method = RequestMethod.GET)
    public ResponseEntity<?> getDeptMedicalService(@PathVariable("deptId") Long deptId) {

        logger.info("getDeptMedicalService API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            logger.info("getDeptMedicalService - Department Medical Services fetching from DB");
            List<MedicalServiceWrapper> deptMedicalSrvc = medicalServicesService.getMedicalServicesByDeptId(deptId);
            logger.info("getDeptMedicalService - Department Medical Services fetched successfully" + deptMedicalSrvc.size());
            response.setResponseMessage(messageBundle.getString("med.service.fetch.success"));
            response.setResponseCode(ResponseEnum.MED_SERVICE_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(deptMedicalSrvc);

            logger.info("getDeptMedicalService API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("getDeptMedicalService exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(httpMethod = "GET", value = "Get Medical Service By Id",
            notes = "This method will return Paginated Medical Services",
            produces = "application/json", nickname = "Medical Service by Id",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Medical Service by Id fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMedicalServiceById(HttpServletRequest request,
                                                   @PathVariable("id") long id) {

        logger.error("getMedicalServiceById API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            if (id <= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getMedicalServiceById API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            logger.error("getMedicalServiceById - Medical Service fetching from DB");
            MedicalServiceWrapper mss = medicalServicesService.findMedicalServicesDetailsById(id);


            mss.setBranches(new ArrayList<>());
            mss.getBranches().addAll(this.branchService.getAllBranches());
            for (BranchWrapperPart b : mss.getBranches()) {
                for (BranchWrapperPart checked : mss.getCheckedBranches()) {
                    if (checked.getId() == b.getId())
                        b.setCheckedBranch(true);
                }
            }

            mss.setDepartments(new ArrayList<>());
            mss.getDepartments().addAll(this.departmentService.getDepartmentsActive());

            /***/
            /*for (DepartmentWrapper d : mss.getDepartments()) {
                for (DepartmentWrapper checked : mss.getCheckedDepartments()) {
                    if (checked.getId() == d.getId())
                        d.setCheckedDepartment(true);
                }
            }*/
            Organization dbOrganization = organizationService.getAllOrgizationData();
            String Zone = dbOrganization.getZone().getName().replaceAll("\\s", "");
            String systemCurrency = dbOrganization.getCurrencyFormat();
            String hoursFormat = dbOrganization.getHoursFormat();
            String dateFormat = dbOrganization.getDateFormat();
            String timeFormat = dbOrganization.getTimeFormat();

            logger.error("getMedicalServiceById - Medical Service fetched successfully");
            if (HISCoreUtil.isValidObject(mss)) {

                if (systemCurrency != null ||  (!systemCurrency.equals(""))) {
                    mss.setStrFee((medicalServicesService.formatCurrencyDisplay((mss.getFee()), systemCurrency)));
                    mss.setStrCost(medicalServicesService.formatCurrencyDisplay((mss.getCost()), systemCurrency));

                }else{
                    mss.setStrFee(String.valueOf(mss.getFee()));
                    mss.setStrCost(String.valueOf(mss.getCost()));
                }

                response.setResponseMessage(messageBundle.getString("med.service.fetch.success"));
                response.setResponseCode(ResponseEnum.MED_SERVICE_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(mss);

                logger.error("getMedicalServiceById API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getMedicalServiceById exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(httpMethod = "POST", value = "saveCode ",
            notes = "This method will Save the Medical Service",
            produces = "application/json", nickname = "Save Medical Service",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Medical Service Saved successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveMedicalService(HttpServletRequest request,
            @RequestPart("myObject") MedicalServiceWrapper createRequest,
            @RequestPart(name = "img", required = false) MultipartFile image) {
        logger.info("saveMedicalService API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();

        try {

            MedicalService alreadyMedicalService = medicalServicesService.findByTitleAndDeletedFalse(createRequest.getName());
            if (HISCoreUtil.isValidObject(alreadyMedicalService)) {
                response.setResponseMessage(messageBundle.getString("med.service.already.exist"));
                response.setResponseCode(ResponseEnum.MED_SERVICE_ALREADY_EXIST.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());

                logger.error("saveMedicalService API - Already Exist.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (image != null) {
                createRequest.setImage(image.getBytes());
            }
            medicalServicesService.saveMedicalService(createRequest);
            response.setResponseMessage(messageBundle.getString("med.service.save.success"));
            response.setResponseCode(ResponseEnum.MED_SERVICE_SAVE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("saveMedicalService API - Successfully saved.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("saveMedicalService API - saving failed.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete Medical Services",
            notes = "This method will Delete Medical Services",
            produces = "application/json", nickname = "Delete Medical Services ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Medical Services deleted successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMedicalServices(HttpServletRequest request,
                                                   @RequestParam("msId") long msId) {
        logger.info("deleteMedicalServices API - Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (msId <= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("deleteMedicalServices API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (medicalServicesService.deleteMedicalService(msId)) {
                response.setResponseMessage(messageBundle.getString("med.service.delete.success"));
                response.setResponseCode(ResponseEnum.MED_SERVICE_DELETE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);
                logger.info("deleteMedicalServices API - deleted Successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setResponseMessage(messageBundle.getString("med.service.delete.error"));
            response.setResponseCode(ResponseEnum.MED_SERVICE_DELETE_ERROR.getValue());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseData(null);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("deleteMedicalServices API - deletion failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "update Medical Service ",
            notes = "This method will Update the Medical Service",
            produces = "application/json", nickname = "Update Medical Service",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Medical Service Updated successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMedicalService(HttpServletRequest request,
            @RequestPart("myObject") MedicalServiceWrapper createRequest,
            @RequestPart(name = "img", required = false) MultipartFile image) {
        logger.info("updateMedicalService API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            if (medicalServicesService.findByNameAgainstId(createRequest.getId(), createRequest.getName())) {
                response.setResponseMessage(messageBundle.getString("med.service.already.exist"));
                response.setResponseCode(ResponseEnum.MED_SERVICE_ALREADY_EXIST.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());

                logger.error("updateMedicalService API - Already Exist.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (image != null) {
                createRequest.setImage(image.getBytes());
            }
            medicalServicesService.updateMedicalService(createRequest);
            response.setResponseData(null);
            response.setResponseMessage(messageBundle.getString("med.service.update.success"));
            response.setResponseCode(ResponseEnum.MED_SERVICE_UPDATE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

            logger.error("updateMedicalService API - Successfully saved.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateMedicalService API - updating failed.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return Response with all search Filtered Medical Services.
     * @author Jamal
     * @description API will return detail of all filtered  Medical Services.
     * @since 17-05-2017
     */
    @ApiOperation(httpMethod = "GET", value = "Search  Medical Services ",
            notes = "This method will return Searched  Medical Services",
            produces = "application/json", nickname = "Get Searched  Medical Services",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Searched  Medical Services fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> searchMedicalServicesByParam(HttpServletRequest request,
                                                          @PathVariable("page") int pageNo,
                                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                          @RequestParam(value = "serviceName") String serviceName,
                                                          @RequestParam(value = "searchCode") String searchCode,
                                                          @RequestParam(value = "branchId") long branchId,
                                                          @RequestParam(value = "departmentId") long departId,
                                                          @RequestParam(value = "serviceFee") double serviceFee) {

        logger.info("searchMedicalServicesByParam API Called");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("med.service.search.not.found"));
        response.setResponseCode(ResponseEnum.MED_SERVICE_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {

            List<MedicalServiceWrapper> medS = this.medicalServicesService.searchMedicalServiceByParam(
                    (serviceName.length() > 0 ? serviceName : null),
                    (searchCode.length() > 0 ? searchCode : null),
                    (branchId > 0 ? branchId : null),
                    (departId > 0 ? departId : null),
                    (serviceFee > 0 ? serviceFee : null),
                    pageNo, pageSize);
            int medServCount = this.medicalServicesService.countSearchMedicalServiceByParam(
                    (serviceName.length() > 0 ? serviceName : null),
                    (searchCode.length() > 0 ? searchCode : null),
                    (branchId > 0 ? branchId : null),
                    (departId > 0 ? departId : null),
                    (serviceFee > 0 ? serviceFee : null));

            if (!HISCoreUtil.isListEmpty(medS)) {
                logger.info("searchMedicalServicesByParam fetched from DB successfully...");
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (medServCount > pageSize) {
                    int remainder = medServCount % pageSize;
                    int totalPages = medServCount / pageSize;
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
                returnValues.put("data", medS);

                response.setResponseMessage(messageBundle.getString("med.service.search.found"));
                response.setResponseCode(ResponseEnum.MED_SERVICE_SEARCH_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("searchMedicalServicesByParam fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("searchMedicalServicesByParam Exception.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
