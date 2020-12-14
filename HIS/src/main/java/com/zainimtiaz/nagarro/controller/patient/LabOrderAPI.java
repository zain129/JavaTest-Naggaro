package com.zainimtiaz.nagarro.controller.patient;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.sd.his.model.*;
import com.zainimtiaz.nagarro.repository.AppointmentRepository;
import com.zainimtiaz.nagarro.repository.LabOrderProjection;
import com.zainimtiaz.nagarro.repository.LabTestRepository;
import com.zainimtiaz.nagarro.repository.LabTestSpecimanRepository;
import com.zainimtiaz.nagarro.service.OrganizationService;
import com.zainimtiaz.nagarro.service.PatientService;
import com.zainimtiaz.nagarro.utill.DateTimeUtil;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.AppointmentWrapper;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.LabOrderUpdateWrapper;
import com.zainimtiaz.nagarro.wrapper.LabOrderWrapper;
import com.zainimtiaz.nagarro.model.LabOrder;
import com.zainimtiaz.nagarro.model.LabTestSpeciman;
import com.zainimtiaz.nagarro.model.Organization;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/patient/laborder")
public class LabOrderAPI {

    @Autowired
    private PatientService patientService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private LabTestRepository labTestRepository;
    private final Logger logger = LoggerFactory.getLogger(LabOrderAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    LabTestSpecimanRepository labTestSpecimanRepository;
    @ApiOperation(httpMethod = "POST", value = "Create LabOrder",
            notes = "This method will Create Lab Order",
            produces = "application/json", nickname = "Create LabOrder",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "LabOrder successfully created", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createLabOrder(HttpServletRequest request,
                                            @RequestPart("myObject") LabOrderUpdateWrapper labOrderWrapper,
                                            @RequestPart(name = "img", required = false) MultipartFile image) {
        logger.info("Create LabOrder API called...");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.add.error"));
        response.setResponseCode(ResponseEnum.LABORDER_ADD_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (image != null) {
                labOrderWrapper.setImage(image.getBytes());
            }
            LabOrderUpdateWrapper labOrder = patientService.saveLabOrderNew(labOrderWrapper);
            if (HISCoreUtil.isValidObject(labOrder)) {
                response.setResponseData(labOrder);
                response.setResponseMessage(messageBundle.getString("laborder.add.success"));
                response.setResponseCode(ResponseEnum.LABORDER_ADD_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("LabOrder created successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }


        } catch (Exception ex) {
            logger.error("LabOrder Creation Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


   @ApiOperation(httpMethod = "GET", value = "Paginated LabOrders",
            notes = "This method will return Paginated LabOrders",
            produces = "application/json", nickname = "Get Paginated LabOrders ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated LabOrders fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaginatedLabOrder(HttpServletRequest request,
                                                     @PathVariable("page") int page,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllLabOrders paginated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.not.found"));
        response.setResponseCode(ResponseEnum.LABORDER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<LabOrderProjection> labordersdata = patientService.getAllLabOrders(page,pageSize);
            Organization dbOrganization=organizationService.getAllOrgizationData();
            String stdDateTime=dbOrganization.getDateFormat()+" "+dbOrganization.getTimeFormat();
            for(int i=0;i<labordersdata.size();i++){
             String readDate=HISCoreUtil.convertDateToString(labordersdata.get(i).getDateTest(),stdDateTime);

                labordersdata.get(i).setDateTest(HISCoreUtil.convertToDate(readDate));

            }
            int countOrders = patientService.totaLabOrders();

            if (!HISCoreUtil.isListEmpty(labordersdata)) {
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
                returnValues.put("data", labordersdata);

                response.setResponseMessage(messageBundle.getString("laborder.fetched.success"));
                response.setResponseCode(ResponseEnum.LABORDER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAllPaginatedLabOrders Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }

           return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated countOrders failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //get order

    @ApiOperation(httpMethod = "PUT", value = "Update LaBOrder ",
            notes = "This method will Update LabOrder",
            produces = "application/json", nickname = "Update LabOrder",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "LabOrder successfully updated", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLabOrder(HttpServletRequest request,
                                                @PathVariable("id") long id,
                                                @RequestPart("myObject") LabOrderUpdateWrapper labOrderWrapper,
                                                @RequestPart(name = "img", required = false) MultipartFile image) {

        logger.info("update LabOrder API called...");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.update.error"));
        response.setResponseCode(ResponseEnum.LABORDER_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {

            LabOrder alreadyExistLabOrder = patientService.findById(id);
            if (HISCoreUtil.isValidObject(alreadyExistLabOrder)) {
                logger.info("LabOrder founded...");
                if (image != null) {
                    labOrderWrapper.setImage(image.getBytes());
                }
                LabOrderUpdateWrapper labOrderUpdated = patientService.updateLabOrderNew(labOrderWrapper, alreadyExistLabOrder);
                if (HISCoreUtil.isValidObject(labOrderUpdated)) {
                    logger.info("LabOrder Updated...");
                    response.setResponseData(labOrderUpdated);
                    response.setResponseMessage(messageBundle.getString("laborder.update.success"));
                    response.setResponseCode(ResponseEnum.LABORDER_UPDATE_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("LabOrder  updated successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                logger.info("LabOrder not found...");
                response.setResponseMessage(messageBundle.getString("laborder.not.found"));
                response.setResponseCode(ResponseEnum.LABORDER_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("laborder not updated...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception ex) {
            logger.error("Update LabOrder Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*@ApiOperation(httpMethod = "GET", value = "Fetch LabOrder",
            notes = "This method will return LabOrder on base of id",
            produces = "application/json", nickname = "Get Single Order",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "LabOrder found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLabOrderById(HttpServletRequest request,
                                           @PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.not.found"));
        response.setResponseCode(ResponseEnum.LABORDER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            LabOrderProjection dbLabOrder = this.patientService.getLabOrderById(id);

            if (HISCoreUtil.isValidObject(dbLabOrder)) {
                response.setResponseData(dbLabOrder);
                response.setResponseCode(ResponseEnum.LABORDER_FOUND.getValue());
                response.setResponseMessage(messageBundle.getString("laborder.fetched.success"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("LabOrder Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("LabOrder Not Found", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    @ApiOperation(httpMethod = "DELETE", value = "Delete LasbOrder",
            notes = "This method will Delete LabOrder on base of id",
            produces = "application/json", nickname = "Delete LabOrder ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "LabOrder Deleted successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLabOrder(HttpServletRequest request,
                                          @PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.delete.error"));
        response.setResponseCode(ResponseEnum.LABORDER_DELETED_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);
        try {
            Boolean LabOrder = patientService.deleteByLabOrder(id);
                if(HISCoreUtil.isValidObject(LabOrder)){
                    response.setResponseData(null);
                    response.setResponseMessage(messageBundle.getString("laborder.delete.success"));
                    response.setResponseCode(ResponseEnum.LABORDER_DELETED_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("LabOrder Deleted successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);}

            }

            catch (Exception ex) {
            logger.error("Unable to delete LabOrder.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //get by id
   /* @ApiOperation(httpMethod = "GET", value = "Paginated LabOrders",
            notes = "This method will return Paginated LabOrders",
            produces = "application/json", nickname = "Get Paginated LabOrders ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated LabOrders fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/order/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaginatedOrdersByPatient(HttpServletRequest request,
                                                     @PathVariable("page") int page,
                                                     @RequestParam (value = "name", required = false) String patientId,
                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllLabOrders paginated.." + patientId);

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.not.found"));
        response.setResponseCode(ResponseEnum.LABORDER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<LabOrderProjection> labordersdata = patientService.getAllLabOrdersByPatient(page,pageSize, Long.valueOf(patientId));
            List<String>  objappoiment = new ArrayList<>();
            Organization dbOrganization=organizationService.getAllOrgizationData();
            String stdDateTime=dbOrganization.getDateFormat()+" "+dbOrganization.getTimeFormat();
            for(int i=0;i<labordersdata.size();i++){
               String readDate=HISCoreUtil.convertDateToString(labordersdata.get(i).getDateTest(),stdDateTime);
            //    labordersdata.get(i).setDateTestString(readDate);
                labordersdata.get(i).setDateTest(HISCoreUtil.convertToDateString(readDate,stdDateTime));
           //     String doctorFirstName=labordersdata.get(i).getAppointment().get(i).getDoctor().getFirstName();
            //    String doctorLastName=labordersdata.get(i).getAppointment().get(i).getDoctor().getLastName();
           //     objappoiment.add(doctorFirstName+""+doctorLastName);
            }
            int countOrders = patientService.totaLabOrders();

            if (!HISCoreUtil.isListEmpty(labordersdata)) {
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
                returnValues.put("data", labordersdata);
             //   returnValues.put("doctors",objappoiment);
                response.setResponseMessage(messageBundle.getString("laborder.fetched.success"));
                response.setResponseCode(ResponseEnum.LABORDER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAllPaginatedLabOrders Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated countOrders failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/

    @ApiOperation(httpMethod = "GET", value = "Paginated LabOrders",
            notes = "This method will return Paginated LabOrders",
            produces = "application/json", nickname = "Get Paginated LabOrders ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated LabOrders fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/order/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllLabTestOrders(HttpServletRequest request,
                                                            @PathVariable("page") int page,
                                                            @RequestParam (value = "name", required = false) String patientId,
                                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllLabOrders paginated.." + patientId);

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.not.found"));
        response.setResponseCode(ResponseEnum.LABORDER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<LabOrderWrapper> labordersdata = patientService.getAllLabOrdersByPatient(patientId);
            List<String>  objappoiment = new ArrayList<>();
            Organization dbOrganization=organizationService.getAllOrgizationData();
            String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
            String systemDateFormat=dbOrganization.getDateFormat();
            String systemTimeFormat=dbOrganization.getTimeFormat();
            String hoursFormat = dbOrganization.getHoursFormat();
            if(hoursFormat.equals("12")){
                if(systemTimeFormat.equals("hh:mm")){
                    systemTimeFormat="hh:mm";
                }else{
                    systemTimeFormat="hh:mm:ss";
                }
            }else{
                if(systemTimeFormat.equals("hh:mm")){
                    systemTimeFormat="HH:mm";
                }else{
                    systemTimeFormat="HH:mm:ss";
                }
            }
            String standardFormatDateTime=systemDateFormat+" "+systemTimeFormat;
            for(int i=0;i<labordersdata.size();i++){
                String dteStrTest = DateTimeUtil.getFormattedDateFromDate(labordersdata.get(i).getTestDate(), HISConstants.DATE_FORMAT_APP);
                String appointDate =DateTimeUtil.getFormattedDateFromDate(labordersdata.get(i).getAppointment().getSchdeulledDate(),HISConstants.DATE_FORMAT_APP);
                Date dteFrom=HISCoreUtil.convertStringDateObjectOrder(dteStrTest);
                Date AppDate=HISCoreUtil.convertStringDateObjectOrder(appointDate);
                String readDateFrom=HISCoreUtil.convertDateToTimeZone(dteFrom,standardFormatDateTime,Zone);
                Date fromDte= DateTimeUtil.getDateFromString(readDateFrom,standardFormatDateTime);
                if(systemDateFormat!=null || !systemDateFormat.equals("")){
                    String  dtelFrom=HISCoreUtil.convertDateToStringWithDateDisplay(fromDte,standardFormatDateTime);
                    String dteAppoint=HISCoreUtil.convertDateToStringWithDateDisplay(AppDate,standardFormatDateTime);
                    labordersdata.get(i).setStrDate(dtelFrom);
                    labordersdata.get(i).setStrAppDate(dteAppoint);
                }
            }
            int countOrders = patientService.totaLabOrders();

            if (!HISCoreUtil.isListEmpty(labordersdata)) {
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
                /*returnValues.put("nextPage", nextPage);
                returnValues.put("prePage", prePage);
                returnValues.put("currPage", currPage);
                returnValues.put("pages", pages);*/
                returnValues.put("data", labordersdata);
                //   returnValues.put("doctors",objappoiment);
                response.setResponseMessage(messageBundle.getString("laborder.fetched.success"));
                response.setResponseCode(ResponseEnum.LABORDER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAllPaginatedLabOrders Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated countOrders failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "GET", value = "Paginated LabOrders",
            notes = "This method will return Paginated LabOrders",
            produces = "application/json", nickname = "Get Paginated LabOrders ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated LabOrders fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/orderId", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaginatedOrdersByPatientId(HttpServletRequest request,
                                                              @RequestParam("orderId") long orderId) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.not.found"));
        response.setResponseCode(ResponseEnum.LABORDER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<LabTest> labordersdata = patientService.ListByLabOrder(Long.valueOf(orderId));

            Organization dbOrganization=organizationService.getAllOrgizationData();
            String stdDateTime=dbOrganization.getDateFormat()+" "+dbOrganization.getTimeFormat();



            if (!HISCoreUtil.isListEmpty(labordersdata)) {



                Map<String, Object> returnValues = new LinkedHashMap<>();

                returnValues.put("data", labordersdata);
                //   returnValues.put("doctors",objappoiment);
                response.setResponseMessage(messageBundle.getString("laborder.fetched.success"));
                response.setResponseCode(ResponseEnum.SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAllPaginatedLabOrders Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("get all paginated countOrders failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // New Setup
    @ApiOperation(httpMethod = "GET", value = "Fetch LabOrder",
            notes = "This method will return LabOrder on base of id",
            produces = "application/json", nickname = "Get Single Order",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "LabOrder found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLabOrderNewById(HttpServletRequest request,
                                             @PathVariable("id") long id,@RequestParam("orderId") long orderId) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("laborder.not.found"));
        response.setResponseCode(ResponseEnum.LABORDER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<LabOrder> dbLabOrder = this.patientService.ListByLabOrderByOrderId(id);

            List<com.zainimtiaz.nagarro.model.LabTest> labTests = labTestRepository.findAllByLabOrder(id);
            List<LabTest> returnListofLab = new ArrayList<>();

            Organization dbOrganization=organizationService.getAllOrgizationData();
            String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
            String systemDateFormat=dbOrganization.getDateFormat();
            String systemTimeFormat=dbOrganization.getTimeFormat();
            String hoursFormat = dbOrganization.getHoursFormat();
            if(hoursFormat.equals("12")){
                if(systemTimeFormat.equals("hh:mm")){
                    systemTimeFormat="hh:mm";
                }else{
                    systemTimeFormat="hh:mm:ss";
                }
            }else{
                if(systemTimeFormat.equals("hh:mm")){
                    systemTimeFormat="HH:mm";
                }else{
                    systemTimeFormat="HH:mm:ss";
                }
            }
            String standardFormatDateTime=systemDateFormat+" "+systemTimeFormat;
            for(int i=0;i<dbLabOrder.size();i++){
                String readDateFrom=HISCoreUtil.convertDateToTimeZone(dbLabOrder.get(i).getDateTest(),standardFormatDateTime,Zone);
                Date dteFrom=HISCoreUtil.convertToDateDetail(readDateFrom,standardFormatDateTime);
                String readDte=HISCoreUtil.convertDateToString(dteFrom,standardFormatDateTime);
                Date fromDte= DateTimeUtil.getDateFromString(readDte,standardFormatDateTime);
                dbLabOrder.get(i).setDateTest(fromDte);

            }

            for(int j=0;j<labTests.size();j++) {
                LabTest testObj = new LabTest();
                if (orderId == labTests.get(j).getId()) {
                    if (labTests.get(j).getLoincCode() != null) {
                        LabTestSpeciman labTestSpecimanObj = this.labTestSpecimanRepository.findTestEntry(labTests.get(j).getLoincCode());
                        testObj.setTestName(labTestSpecimanObj.getTestName());
                        testObj.setNormalRange(labTestSpecimanObj.getMinNormalRange() + "-" + labTestSpecimanObj.getMaxNormalRange());
                    } else {
                        testObj.setNormalRange("");
                    }

                    testObj.setDescription(labTests.get(j).getDescription());
                    testObj.setResultValue(labTests.get(j).getResultValue());
                    testObj.setLoincCode(labTests.get(j).getLoincCode());
                    testObj.setUnits(labTests.get(j).getUnits());

                    testObj.setId(String.valueOf(labTests.get(j).getId()));
                    returnListofLab.add(testObj);
                }
            }
            List<AppointmentWrapper> apptFutureWrapperList = new ArrayList<>();
            List<AppointmentWrapper> apptPastWrapperList = new ArrayList<>();
            List<AppointmentWrapper> listOfAppointments = appointmentRepository.findAllAppointmentsByPatient(dbLabOrder.get(0).getPatient().getId());
            Map<Boolean, List<AppointmentWrapper>> listOfApp = listOfAppointments.stream()
                    .collect(Collectors.partitioningBy(x -> x.getCompareDate()
                            .toInstant().isAfter(Instant.now())));
            /*patientWrapper.setFutureAppointments(listOfApp.get(true));
            patientWrapper.setPastAppointments(listOfApp.get(false));*/

            if (!HISCoreUtil.isListEmpty(dbLabOrder)) {
                Map<String, Object> returnValues = new LinkedHashMap<>();
                dbLabOrder.get(0).setLabTests(null);
                returnValues.put("data", dbLabOrder);
                returnValues.put("labTest",returnListofLab);
                returnValues.put("appointment",listOfApp);

                response.setResponseData(returnValues);

                response.setResponseCode(ResponseEnum.LABORDER_FOUND.getValue());
                response.setResponseMessage(messageBundle.getString("laborder.fetched.success"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("LabOrder Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("LabOrder Not Found", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
