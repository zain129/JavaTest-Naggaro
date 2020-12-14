package com.zainimtiaz.nagarro.controller.patient;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.ICDCode;
import com.zainimtiaz.nagarro.model.Organization;
import com.zainimtiaz.nagarro.repository.ICDCodeRepository;
import com.zainimtiaz.nagarro.service.OrganizationService;
import com.zainimtiaz.nagarro.service.PatientService;
import com.zainimtiaz.nagarro.service.ProblemService;
import com.zainimtiaz.nagarro.utill.DateTimeUtil;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.ProblemWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

/*
 * @author    : Muhammad Jamal
 * @Date      : 13-August-18
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
 * @Package   : com.sd.his.controller.patient.PatientHistoryAPI
 * @FileName  : PatientHistoryAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@RestController
@RequestMapping("/patient/history")
public class PatientHistoryAPI {

    private final Logger logger = LoggerFactory.getLogger(PatientHistoryAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");
    @Autowired
    private PatientService patientService;
    @Autowired
    private ProblemService problemService;

    @Autowired
    ICDCodeRepository icdCodeRepository;

    @Autowired
    private OrganizationService organizationService;

    @ApiOperation(httpMethod = "POST", value = "Save patient Problem",
            notes = "This method will save the patient Problem.",
            produces = "application/json", nickname = "Save patient Problem",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save patient Problem successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/problem/save", method = RequestMethod.POST)//, consumes = "multipart/form-data"
    public ResponseEntity<?> savePatientProblem(HttpServletRequest request,
                                                @RequestBody ProblemWrapper problemWrapper) {
        logger.info("savePatientProblem API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            if (problemWrapper.getPatientId() <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.problem.patient.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_PATIENT_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("savePatientProblem API - Required patient id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (problemWrapper.getAppointmentId() <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.problem.save.appointment.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_VERSION_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("savePatientProblem API - Required version ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (problemWrapper.getSelectedICDVersionId() <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.problem.save.version.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_VERSION_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("savePatientProblem API - Required version ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (problemWrapper.getSelectedCodeId() <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.problem.save.code.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_CODE_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("savePatientProblem API - Required code ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (problemWrapper.getDatePrescribedDate() == null) {
                response.setResponseMessage(messageBundle.getString("patient.problem.save.diagnosis.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_VERSION_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("savePatientProblem API - Required version ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            problemService.savePatientProblem(problemWrapper);

            response.setResponseMessage(messageBundle.getString("patient.problem.save.success"));
            response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("savePatientProblem API - successfully saved.");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("savePatientProblem exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "POST", value = "Update patient Problem",
            notes = "This method will Update the patient Problem.",
            produces = "application/json", nickname = "Update patient Problem",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Update patient Problem successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/problem/update", method = RequestMethod.PUT)//, consumes = "multipart/form-data"
    public ResponseEntity<?> updatePatientProblem(HttpServletRequest request,
                                                  @RequestBody ProblemWrapper problemWrapper) {
        logger.info("updatePatientProblem API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {


            if (problemWrapper.getPatientId() <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.problem.patient.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_PATIENT_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updatePatientProblem API - Required patient id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (problemWrapper.getAppointmentId() <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.problem.save.appointment.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_VERSION_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updatePatientProblem API - Required version ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (problemWrapper.getSelectedICDVersionId() <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.problem.save.version.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_VERSION_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updatePatientProblem API - Required version ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (problemWrapper.getSelectedCodeId() <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.problem.save.code.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_CODE_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updatePatientProblem API - Required code ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (problemWrapper.getDateDiagnosis() == null || problemWrapper.getDateDiagnosis().isEmpty()) {
                response.setResponseMessage(messageBundle.getString("patient.problem.save.diagnosis.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_VERSION_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updatePatientProblem API - Required version ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            problemService.updatePatientProblem(problemWrapper);

            response.setResponseMessage(messageBundle.getString("patient.problem.update.success"));
            response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_SAVE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("updatePatientProblem API - successfully saved.");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updatePatientProblem exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete patient Problem",
            notes = "This method will Delete the patient Problem",
            produces = "application/json", nickname = "Delete patient Problem",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted patient Problem successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/problem/delete/{problemId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePatientProblem(HttpServletRequest request,
                                                  @PathVariable("problemId") long problemId) {
        logger.info("deletePatientProblem API - Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (problemId <= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("deletePatientProblem API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            this.problemService.deleteProblemById(problemId);
            response.setResponseMessage(messageBundle.getString("patient.problem.delete.success"));
            response.setResponseCode(ResponseEnum.PATIENT_DELETE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);
            logger.info("deletePatientProblem API - Deleted Successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("deletePatientProblem API - deleted failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "patient",
            notes = "This method will return User on base of id",
            produces = "application/json", nickname = "Get Single User",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "patient found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/problem/get", method = RequestMethod.GET)
    public ResponseEntity<?> getProblemById(HttpServletRequest request, @RequestParam("problemId") int id) {

        GenericAPIResponse response = new GenericAPIResponse();
        try {
            ProblemWrapper problemWrapper = this.problemService.getProblemById(id);
            ICDCode icdCode=icdCodeRepository.findByCode(problemWrapper.getCodeName());
            problemWrapper.setProblemName(icdCode.getProblem());
            if (HISCoreUtil.isValidObject(problemWrapper)) {
                response.setResponseData(problemWrapper);
                response.setResponseMessage(messageBundle.getString("patient.found"));
                response.setResponseCode(ResponseEnum.USER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("getProblemById User Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("patient.search.not.found"));
                response.setResponseCode(ResponseEnum.PATIENT_FETCHED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("getProblemById Not Found ...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getProblemById Exception", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "GET Paginated Problems",
            notes = "This method will return Paginated  Problems",
            produces = "application/json", nickname = "GET Paginated  Problems",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated  Problems fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/problem/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaginatedProblem(HttpServletRequest request,
                                                 @PathVariable("page") int page,
                                                 @RequestParam("patientId") String patientId,
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        logger.error("getPaginatedProblem API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            logger.error("getPaginatedProblem -  fetching from DB");

            if (patientId.equals("0") || Long.valueOf(patientId) <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.problem.patient.required"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_PATIENT_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getPaginatedProblem API - Required patient id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Pageable pageable = new PageRequest(page, pageSize);
            Page<ProblemWrapper> patientProblems = this.problemService.findPaginatedProblem(pageable, Long.valueOf(patientId));
            List<ProblemWrapper> list = new ArrayList<>();
            int count = ((int) (patientProblems.getTotalElements()));
            Organization dbOrganization=organizationService.getAllOrgizationData();
            String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
            String systemDateFormat=dbOrganization.getDateFormat();
            String systemTimeFormat=dbOrganization.getTimeFormat();
            String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
            String hoursFormat = dbOrganization.getHoursFormat();
            String standardSystem=systemDateFormat+" "+systemTimeFormat;
            logger.error("getPaginatedProblem - fetched successfully");
                Date dteDia=null;
            if (patientProblems != null) {
                for (ProblemWrapper problemWrapper : patientProblems) {
                    ICDCode icdCode=icdCodeRepository.findByCode(problemWrapper.getCodeName());
                    problemWrapper.setProblemName(icdCode.getProblem());
                    dteDia= DateTimeUtil.getDateFromString(problemWrapper.getDateDiagnosis(), "yyyy-MM-dd hh:mm:ss");
                    String dZoneDate = HISCoreUtil.convertDateToTimeZone(dteDia, "yyyy-MM-dd hh:mm:ss", Zone);
                    if (systemDateFormat != null || !systemDateFormat.equals("")) {
                        String sdDate=HISCoreUtil.convertDateToStringWithDateDisplay(dteDia,systemDateFormat);
                        problemWrapper.setDateDiagnosis(sdDate);
                    }
                    list.add(new ProblemWrapper(problemWrapper));
                }
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (count > pageSize) {
                    int remainder = count % pageSize;
                    int totalPages = count / pageSize;
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
                returnValues.put("data", list);

                response.setResponseMessage(messageBundle.getString("patient.problem.fetch.success"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_FETCHED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getPaginatedProblem API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getPaginatedProblem exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "GET Paginated Problems",
            notes = "This method will return Paginated  Problems",
            produces = "application/json", nickname = "GET Paginated  Problems",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated  Problems fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/problem/status/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaginatedProblemByStatusAndPatientId(HttpServletRequest request,
                                                                     @PathVariable("page") int page,
                                                                     @RequestParam("status") String status,
                                                                     @RequestParam("selectedPatientId") String selectedPatientId,
                                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        logger.error("getPaginatedProblemByStatus API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            logger.error("getPaginatedProblemByStatus -  fetching from DB");
            Pageable pageable = new PageRequest(page, pageSize);
            Page<ProblemWrapper> patientProblems = this.problemService.getProblemsByStatusAndPatientId(pageable, status, Long.valueOf(selectedPatientId));
            List<ProblemWrapper> list = new ArrayList<>();
            int count = ((int) (patientProblems.getTotalElements()));

            Organization dbOrganization=organizationService.getAllOrgizationData();
        //    asdasdadsa
            String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
            //  Date dteFrom=new Date();
            //  Date dteTo=new Date();
            String systemDateFormat=dbOrganization.getDateFormat();
            String systemTimeFormat=dbOrganization.getTimeFormat();
            String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
         //   String standardFormatDateTime=systemDateFormat+" "+systemTimeFormat;
            String hoursFormat = dbOrganization.getHoursFormat();
            logger.error("getPaginatedProblemByStatus - fetched successfully");

            if (patientProblems != null) {
                for (ProblemWrapper problemWrapper : patientProblems) {
                    String dateDiagnose="";
                    ICDCode icdCode=icdCodeRepository.findByCode(problemWrapper.getCodeName());
                    problemWrapper.setProblemName(icdCode.getProblem());
                    Date dteFrom=DateTimeUtil.getDateFromString(problemWrapper.getDateDiagnosis(),"yyyy-MM-dd hh:mm:ss");
                    String readDateFrom=HISCoreUtil.convertDateToTimeZone(dteFrom,"yyyy-MM-dd hh:mm:ss",Zone);
                    Date fromDte= DateTimeUtil.getDateFromString(readDateFrom,"yyyy-MM-dd hh:mm:ss");
                    if(systemDateFormat!=null || !systemDateFormat.equals("")){

                        String  dtelFrom=HISCoreUtil.convertDateToStringWithDateDisplay(fromDte,systemDateFormat);

                        if(systemTimeFormat!=null || !systemTimeFormat.equals("")){

                            SimpleDateFormat localDateFormat = new SimpleDateFormat(systemTimeFormat);
                            String time = localDateFormat.format(fromDte);
                            System.out.println(time);
                            String displayTime=HISCoreUtil.convertToHourFormat(time,hoursFormat,systemTimeFormat);
                            dateDiagnose=dtelFrom+" "+displayTime;
                        }

                        problemWrapper.setDateDiagnosis(dateDiagnose);

                    }
                 //   problemWrapper.setDateDiagnosis(readDateFrom);
                    list.add(new ProblemWrapper(problemWrapper));
                }

                Integer nextPage, prePage, currPage;
                int[] pages;

                if (count > pageSize) {
                    int remainder = count % pageSize;
                    int totalPages = count / pageSize;
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
                returnValues.put("data", list);

                response.setResponseMessage(messageBundle.getString("patient.problem.fetch.success"));
                response.setResponseCode(ResponseEnum.PATIENT_PROBLEM_FETCHED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getPaginatedProblemByStatus API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getPaginatedProblemByStatus exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
