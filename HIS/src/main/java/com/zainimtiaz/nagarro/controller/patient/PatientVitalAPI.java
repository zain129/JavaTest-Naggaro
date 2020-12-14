package com.zainimtiaz.nagarro.controller.patient;

import com.zainimtiaz.nagarro.controller.setting.EmailConfigurationController;
import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.Organization;
import com.zainimtiaz.nagarro.model.PatientVital;
import com.zainimtiaz.nagarro.service.OrganizationService;
import com.zainimtiaz.nagarro.service.PatientVitalService;
import com.zainimtiaz.nagarro.utill.DateTimeUtil;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.sd.his.wrapper.*;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.PatientVitalWrapper;
import com.zainimtiaz.nagarro.wrapper.VitaPatientWrapper;
import com.zainimtiaz.nagarro.wrapper.VitalWrapper;
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

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
@RequestMapping("/PatientVital")
public class PatientVitalAPI {

    @Autowired
    PatientVitalService vitalServices;

    private final Logger logger = LoggerFactory.getLogger(EmailConfigurationController.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private OrganizationService organizationService;

    @ApiOperation(httpMethod = "POST", value = "Save Vital Setup Configuration",
            notes = "This method will save Vital Setup Configuration",
            produces = "application/json", nickname = "Save Vital Setup Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/savePatientVital", method = RequestMethod.POST)
    public ResponseEntity<?> saveVitalSetup(HttpServletRequest request, @RequestBody PatientVitalWrapper vitalRequestWrapper) {

        logger.error("Save Vital  API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            vitalServices.save(vitalRequestWrapper);
            //    response.setResponseData(vitalServices.getAll());
            response.setResponseMessage(messageBundle.getString("vital.setup.save.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Vital  saved successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Vital Setup  Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("vital.setup.update.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "GET", value = "get Vital Setup Configurations",
            notes = "This method will get Vital Setup Configurations",
            produces = "application/json", nickname = "Get Vital Setup Configurations",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/getPatientVital", method = RequestMethod.GET)
    public ResponseEntity<?> getVitalSetup(){
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            response.setResponseData(vitalServices.getAll());


            response.setResponseMessage(messageBundle.getString("vital.setup.fetched.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Vital Setup data fetch successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Vital Setup data  not fetched successfully/ Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("vital.setup.fetch.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "PUT", value = "Update Vital",
            notes = "This method will Update Vital",
            produces = "application/json", nickname = "Update Vital",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Vital  Updated successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateVitalType(HttpServletRequest request,
                                               @RequestBody VitaPatientWrapper vitalRequestWrapper) {


        logger.info("Update Payment API - Update Vital By id:" + vitalRequestWrapper.getId());
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("vital.setup.update.success"));
        response.setResponseCode(ResponseEnum.SUCCESS.getValue());
        response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (vitalRequestWrapper.getId()<= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("update Payment Type API - parameter is insufficient...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            vitalServices.update(vitalRequestWrapper);
            response.setResponseMessage(messageBundle.getString("paymentType.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);
            logger.info("update Customer API - Customer updated successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("update Payment Type  API - Exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "DELETE", value = "DELETE Payment Type",
            notes = "This method will Delete Payment Type",
            produces = "application/json", nickname = "Delete Payment Type",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Delete Payment Type successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{vitalId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePaymentType(HttpServletRequest request,
                                               @PathVariable("vitalId")  long  vitalId) {

        logger.error("Delete Payment Type API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("paymentType.delete.error"));
        response.setResponseCode(ResponseEnum.SUCCESS.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("Delete Customer  -  fetching from DB for existence");

            if (vitalId <= 0) {
                response.setResponseMessage(messageBundle.getString("cli.dpts.delete.id"));
                response.setResponseCode(ResponseEnum.PAYMENTTYPE_DELETE_ID.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);

                logger.error("delete - Please provide proper Id");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            vitalServices.deleteVital(vitalId);
            response.setResponseMessage(messageBundle.getString("vital.delete.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);

            logger.error("Delete Payment Api - deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("Delete  Api exception..", ex.fillInStackTrace());
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
    @RequestMapping(value = "/get/{vitalId}", method = RequestMethod.GET)
    public ResponseEntity<?> getVitalById(HttpServletRequest request,
                                          @PathVariable("vitalId") long vitalId) {

        logger.error("getDepartmentsByMedicalServiceId API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            logger.error("Vital -  fetching from DB");
            PatientVital vital= vitalServices.getById(vitalId);
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

            response.setResponseData(vital);

            logger.info("API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("exception..", ex.fillInStackTrace());
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

        logger.error("getPaginated API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {

            logger.error("getPaginated -  fetching from DB");
            if (patientId == null || Long.valueOf(patientId) <= 0) {
                response.setResponseCode(ResponseEnum.ERROR.getValue());
                response.setResponseMessage(messageBundle.getString("document.patient.required"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("getPaginatedDocumentation API - patient required");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Pageable pageable = new PageRequest(page, pageSize);
            // Patient_OrderWrapper orderWrapper
            List<PatientVitalWrapper> vitalList = this.vitalServices.getPaginatedOrder(pageable, Long.valueOf(patientId));
            String chief="";
            String createdDate="";
            Organization dbOrganization = organizationService.getAllOrgizationData();
            String systemDateFormat = dbOrganization.getDateFormat();
            String Zone = dbOrganization.getZone().getName().replaceAll("\\s", "");
          //  String systemDateFormat=dbOrganization.getDateFormat();
          //  String systemTimeFormat=dbOrganization.getTimeFormat();
          //  String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
          //  String hoursFormat = dbOrganization.getHoursFormat();
         //   String standardSystem=systemDateFormat;

            for(int i=0;i<vitalList.size();i++){
                chief=vitalList.get(i).getChiefComplaint();
                vitalList.get(i).setName(vitalList.get(i).getName()+"("+vitalList.get(i).getUnit()+")");
                Date scheduledDate = HISCoreUtil.convertStringDateObject(vitalList.get(i).getVitalStrDate());
                String readDate = HISCoreUtil.convertDateToTimeZone(scheduledDate, "YYYY-MM-dd hh:mm:ss", Zone);
                Date dteStartedDate = DateTimeUtil.getDateFromString(readDate, "yyyy-MM-dd hh:mm:ss");
                String sdDate=HISCoreUtil.convertDateToStringWithDateDisplay(dteStartedDate,systemDateFormat);
                createdDate=sdDate;

            }
           // String chief=vitalList.get(0).getChiefComplaint();
           // int documentWrappersCount = vitalServices.countPaginatedDocuments();

            logger.error("getPaginatedDocumentation - fetched successfully");

            if (!HISCoreUtil.isListEmpty(vitalList)) {
              /*  Integer nextPage, prePage, currPage;
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
                }*/

                Map<String, Object> returnValues = new LinkedHashMap<>();
               /* returnValues.put("nextPage", nextPage);
                returnValues.put("prePage", prePage);
                returnValues.put("currPage", currPage);
                returnValues.put("pages", pages);*/
                returnValues.put("data", vitalList);
                returnValues.put("chiefComplaint",chief);
                returnValues.put("Date",createdDate);

                response.setResponseMessage(messageBundle.getString("document.paginated.success"));
                response.setResponseCode(ResponseEnum.SUCCESS.getValue());
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




    @ApiOperation(httpMethod = "POST", value = "Save Vital Setup ",
            notes = "This method will save Vital Setup ",
            produces = "application/json", nickname = "Save Vital Setup ",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/savePatientVitalList", method = RequestMethod.POST)
    public ResponseEntity<?> saveVitalSetupList(HttpServletRequest request, @RequestBody List<VitalWrapper> vitalRequestWrapper,
                                                @RequestParam("selectedPatientId") String selectedPatientId) {

        logger.error("Save Vital  API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            vitalServices.saveListVital(vitalRequestWrapper,selectedPatientId);
            //    response.setResponseData(vitalServices.getAll());
            response.setResponseMessage(messageBundle.getString("vital.setup.save.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Vital  saved successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Vital Setup  Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("vital.setup.update.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
