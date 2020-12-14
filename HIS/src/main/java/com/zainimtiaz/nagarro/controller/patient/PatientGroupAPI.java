package com.zainimtiaz.nagarro.controller.patient;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.service.PatientGroupService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.PatientGroupWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

/**
 * Created by jamal on 10/23/2018.
 */
@RestController
@RequestMapping("/patient/group/")
public class PatientGroupAPI {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(PatientGroupAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private PatientGroupService patientGroupService;

    @ApiOperation(httpMethod = "GET", value = "All Patient Groups",
            notes = "This method will return all patient groups",
            produces = "application/json", nickname = "Get All Patient Groups",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All patient groups found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "get/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPatientGroups(HttpServletRequest request) {
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            List<PatientGroupWrapper> patientGroupWrapper = this.patientGroupService.getAllPatientGroups();

            if (HISCoreUtil.isValidObject(patientGroupWrapper)) {
                Map<String, Object> returnValues = new LinkedHashMap<>();
                returnValues.put("data", patientGroupWrapper);
                response.setResponseData(returnValues);
                response.setResponseMessage(messageBundle.getString("patient.group.get.success"));
                response.setResponseCode(ResponseEnum.PATIENT_GROUP_GET_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("All Patients groups found successfully...");
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("patient.group.deleted"));
                response.setResponseCode(ResponseEnum.PATIENT_GROUP_GET_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("All Patients groups Not Found Or deleted by other user before your click.");
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("getAllPatientGroups Exception", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "POST", value = "Save Patient Group",
            notes = "This method will Save  Patient Group",
            produces = "application/json", nickname = "Save  Patient Group",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save  Patient Group successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseEntity<?> savePatientGroup(HttpServletRequest request,
                                              @RequestBody PatientGroupWrapper patientGroupWrapper) {

        logger.info("savePatientGroup API Called...!!");
        GenericAPIResponse response = new GenericAPIResponse();


        try {

            if (HISCoreUtil.isNull(patientGroupWrapper.getName())) {
                response.setResponseMessage(messageBundle.getString("patient.group.save.name.required"));
                response.setResponseCode(ResponseEnum.PATIENT_GROUP_SAVE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("savePatientGroup , please provide name.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (this.patientGroupService.isPatientGroupByNameExist(patientGroupWrapper.getName())) {
                response.setResponseMessage(messageBundle.getString("patient.group.save.name.duplicate"));
                response.setResponseCode(ResponseEnum.PATIENT_GROUP_SAVE_NAME_DUBPLUCATE.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("savePatientGroup , please provide name.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            patientGroupService.savePatientGroup(patientGroupWrapper);

            response.setResponseMessage(messageBundle.getString("patient.group.save.success"));
            response.setResponseCode(ResponseEnum.PATIENT_GROUP_SAVE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);
            logger.info("The savePatientGroup saved successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("savePatientGroup exception..", ex.fillInStackTrace());
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
    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaginatedPatientGroups(HttpServletRequest request,
                                                          @PathVariable("page") int page,
                                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        logger.error("getAllPaginatedPatientGroups API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            logger.error("getAllPaginatedPatientGroups - fetching from DB");
            List<PatientGroupWrapper> patientGroupWrappers = patientGroupService.getAllPaginatedPatientGroup(new PageRequest(page, pageSize));
            int count = patientGroupService.countAllPaginatedPatientGroup();


            if (!HISCoreUtil.isListEmpty(patientGroupWrappers)) {
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
                returnValues.put("data", patientGroupWrappers);

                response.setResponseMessage(messageBundle.getString("patient.group.fetch.success"));
                response.setResponseCode(ResponseEnum.PATIENT_GROUP_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getAllPaginatedPatientGroups API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getAllPaginatedPatientGroups exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "patient group",
            notes = "This method will return patient group on base of id",
            produces = "application/json", nickname = "Get Single User",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = " patient group found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResponseEntity<?> getPatientGroupById(HttpServletRequest request,
                                                 @RequestParam("patientGroupId") int id) {

        GenericAPIResponse response = new GenericAPIResponse();
        try {
            PatientGroupWrapper patientGroupWrapper = this.patientGroupService.getPatientGroupById(id);
            if (HISCoreUtil.isValidObject(patientGroupWrapper)) {
                response.setResponseData(patientGroupWrapper);
                response.setResponseMessage(messageBundle.getString("patient.group.get.success"));
                response.setResponseCode(ResponseEnum.PATIENT_GROUP_GET_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("getPatientGroupById User Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("patient.group.deleted"));
                response.setResponseCode(ResponseEnum.PATIENT_GROUP_GET_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("getPatientGroupById Not Found Or deleted by other user before your click.");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getPatientGroupById Exception", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Update patient Allergy",
            notes = "This method will Update the patient Allergy.",
            produces = "application/json", nickname = "Update patient Allergy",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Update patient Allergy successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePatientGroup(HttpServletRequest request,
                                                @RequestBody PatientGroupWrapper patientGroupWrapper) {
        logger.info("updatePatientGroup API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            if (patientGroupWrapper.getId() <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.group.save.id.required"));
                response.setResponseCode(ResponseEnum.PATIENT_GROUP_SAVE_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updatePatientGroup API - Required Id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (patientGroupWrapper.getName().trim().equals("")) {
                response.setResponseMessage(messageBundle.getString("patient.group.save.name.required"));
                response.setResponseCode(ResponseEnum.PATIENT_GROUP_SAVE_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updatePatientGroup API - Required patient id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (this.patientGroupService.isPatientGroupByNameExistAndNotEqualId(patientGroupWrapper.getId(), patientGroupWrapper.getName())) {
                response.setResponseMessage(messageBundle.getString("patient.group.update.name.duplicate"));
                response.setResponseCode(ResponseEnum.PATIENT_GROUP_SAVE_NAME_DUBPLUCATE.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("updatePatientGroup API - successfully saved.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            this.patientGroupService.updatePatientGroup(patientGroupWrapper);
            response.setResponseMessage(messageBundle.getString("patient.group.update.success"));
            response.setResponseCode(ResponseEnum.PATIENT_GROUP_UPDATE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("updatePatientGroup API - successfully saved.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updatePatientGroup exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePatientGroup(HttpServletRequest request,
                                                @RequestParam("patientGroupId") long patientGroupId) {

        logger.error("deletePatientGroup API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            logger.error("deletePatientGroup - dpt fetching from DB for existence");

            if (patientGroupId <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.group.delete.id.required"));
                response.setResponseCode(ResponseEnum.PATIENT_GROUP_UPDATE_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);

                logger.error("deletePatientGroup - Please provide proper department id.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (this.patientGroupService.hasChild(patientGroupId)) {
                response.setResponseMessage(messageBundle.getString("patient.group.delete.child"));
                response.setResponseCode(ResponseEnum.PATIENT_GROUP_DELETE_CHILD.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);

                logger.error("deletePatientGroup - department has child record so you cannot delete it. First , delete its child record then you can delete it");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            patientGroupService.deletePatientGroup(patientGroupId);
            response.setResponseMessage(messageBundle.getString("patient.group.delete.success"));
            response.setResponseCode(ResponseEnum.PATIENT_GROUP_DELETE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);

            logger.error("deletePatientGroup - deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("deletePatientGroup exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
