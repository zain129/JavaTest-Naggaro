package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.Appointment;
import com.zainimtiaz.nagarro.service.AppointmentService;
import com.zainimtiaz.nagarro.service.UserService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.AppointmentWrapper;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.response.MedicalServicesDoctorWrapper;
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
 * @author    : Waqas Kamran
 * @Date      : 8-Jun-18
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
 * @Package   : com.sd.his.controller.appointment
 * @FileName  : AppointmentAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@RestController
@RequestMapping("/appointment")
public class AppointmentAPI {

    Logger logger = LoggerFactory.getLogger(AppointmentAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @ApiOperation(httpMethod = "GET", value = "All Appointments",
            notes = "This method will return All Appointments",
            produces = "application/json", nickname = "All Appointments",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Appointments  fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAppointments(HttpServletRequest request) {

        logger.error("getAllAppointments API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("appointment.fetch.error"));
        response.setResponseCode(ResponseEnum.APPT_FETCHED_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getAllAppointments - appt fetching from DB");
            List<AppointmentWrapper> appts = appointmentService.findAllAppointments();
            logger.error("getAllAppointments - appt fetched successfully");

            if (HISCoreUtil.isListEmpty(appts)) {
                response.setResponseMessage(messageBundle.getString("appointment.not-found"));
                response.setResponseCode(ResponseEnum.APPT_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getAllAppointments API - appts not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("appointment.fetched.success"));
            response.setResponseCode(ResponseEnum.APPT_FETCHED_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(appts);

            logger.error("getAllAppointments API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllAppointments exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "GET Paginated Appointments",
            notes = "This method will return Paginated Appointments",
            produces = "application/json", nickname = "GET Paginated Appointments",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated Appointments fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaginatedAppointments(HttpServletRequest request,
                                                         @PathVariable("page") int page,
                                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        logger.error("getAllPaginatedAppointments API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("appointment.fetch.error"));
        response.setResponseCode(ResponseEnum.APPT_FETCHED_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getAllPaginatedAppointments -  fetching from DB");
            List<AppointmentWrapper> appointments = appointmentService.findAllPaginatedAppointments(page, pageSize);
            int patientCount = appointmentService.countAllAppointments();

            logger.error("getAllPaginatedAppointments - fetched successfully");
            if (!HISCoreUtil.isListEmpty(appointments)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (patientCount > pageSize) {
                    int remainder = patientCount % pageSize;
                    int totalPages = patientCount / pageSize;
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
                returnValues.put("data", appointments);

                response.setResponseMessage(messageBundle.getString("appointment.fetched.success"));
                response.setResponseCode(ResponseEnum.APPT_FETCHED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getAllPaginatedAppointments API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getAllPaginatedAppointments exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "POST", value = "Create Appointment",
            notes = "This method will Create Appointment",
            produces = "application/json", nickname = "Create Appointment",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Appointment successfully created", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=*/*")
    public ResponseEntity<?> createAppointment(HttpServletRequest request,
                                               @RequestBody AppointmentWrapper appointmentWrapper) {
        logger.info("Create Appointment API called...");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("appointment.add.error"));
        response.setResponseCode(ResponseEnum.APPT_SAVED_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
/*            User alreadyExist = userService.findByUserName(appointmentWrapper.getProblem());
            if (HISCoreUtil.isValidObject(alreadyExist)) {
                response.setResponseMessage(messageBundle.getString("user.add.already-found.error"));
                response.setResponseCode(ResponseEnum.USER_ALREADY_EXIST_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("User already exist with the same name...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }*/

            String savedAppointment = appointmentService.saveAppointment(appointmentWrapper);

            if (savedAppointment.equalsIgnoreCase("success")) {
                // response.setResponseData(savedAppointment);
                response.setResponseMessage(messageBundle.getString("appointment.add.success"));
                response.setResponseCode(ResponseEnum.APPT_SAVED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Appointment created successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (savedAppointment.equalsIgnoreCase("doctorShift")) {
                // response.setResponseData(savedAppointment);
                response.setResponseMessage(messageBundle.getString("doctor.shift.conflict"));
                response.setResponseCode(ResponseEnum.APPT_DOCTOR_SHIFT_CONFLICT.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Doctor is not available at this time...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (savedAppointment.equalsIgnoreCase("dayConflict")) {
                // response.setResponseData(savedAppointment);
                response.setResponseMessage(messageBundle.getString("working.day.conflict"));
                response.setResponseCode(ResponseEnum.APPT_DOCTOR_WORKING_DAYS_CONFLICT.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Doctor is not available on this day...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (savedAppointment.equalsIgnoreCase("onVacation")) {
                response.setResponseMessage(messageBundle.getString("doctor.on.vacation"));
                response.setResponseCode(ResponseEnum.APPT_DOCTOR_ON_VACATION.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Doctor is on vacation...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else if (savedAppointment.equalsIgnoreCase("already")) {
                response.setResponseMessage(messageBundle.getString("appointment.already.exist"));
                response.setResponseCode(ResponseEnum.APPT_ALREADY_EXISTS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Appointment Already Exists...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseMessage(messageBundle.getString("appointment.add.error"));
                response.setResponseCode(ResponseEnum.APPT_SAVED_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }


        } catch (Exception ex) {
            logger.error("Appointment Creation Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(httpMethod = "PUT", value = "Update Appointment ",
            notes = "This method will Update Appointment",
            produces = "application/json", nickname = "Update Appointment",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Appointment successfully updated", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAppointment(HttpServletRequest request,
                                               @PathVariable("id") long id,
                                               @RequestBody AppointmentWrapper appointmentWrapper) {

        logger.info("update Appointment API called...");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("appointment.update.error"));
        response.setResponseCode(ResponseEnum.APPT_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            Appointment alreadyExistAppointment = appointmentService.findById(id);
            if (HISCoreUtil.isValidObject(alreadyExistAppointment)) {
                logger.info("Appointment founded...");
                String savedAppointment = appointmentService.updateAppointment(appointmentWrapper, alreadyExistAppointment);
                if (savedAppointment.equalsIgnoreCase("success")) {
                    logger.info("Appointment Updated...");
                    response.setResponseData(null);
                    response.setResponseMessage(messageBundle.getString("appointment.update.success"));
                    response.setResponseCode(ResponseEnum.APPT_UPDATE_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("Appointment updated successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else if (savedAppointment.equalsIgnoreCase("already")) {
                    response.setResponseMessage(messageBundle.getString("appointment.already.exist"));
                    response.setResponseCode(ResponseEnum.APPT_ALREADY_EXISTS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("Appointment Already Exists...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                logger.info("Appointment not found...");
                response.setResponseMessage(messageBundle.getString("appointment.not-found"));
                response.setResponseCode(ResponseEnum.APPT_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Appointment not updated...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception ex) {
            logger.error("Update Appointment Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(httpMethod = "GET", value = "Search Appointment",
            notes = "This method will Search Appointment",
            produces = "application/json", nickname = "Search Appointment",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Search Appointment successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> searchByDoctorAndBranch(HttpServletRequest request,
                                                     @RequestParam(value = "doctorId") Long doctorId,
                                                     @RequestParam(value = "branchId") Long branchId) {

        logger.error("Search Appointment API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("appointment.fetch.error"));
        response.setResponseCode(ResponseEnum.APPT_FETCHED_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<AppointmentWrapper> appointments = appointmentService.getPageableSearchedAppointments(doctorId, branchId);
            //  int countSearchedAppointments = appointmentService.countSearchedAppointments(doctorId,branchId);
            if (HISCoreUtil.isListEmpty(appointments)) {
                response.setResponseMessage(messageBundle.getString("appointment.search.not.found"));
                response.setResponseCode(ResponseEnum.APPT_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("searchAppointment - Appointment not found.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setResponseMessage(messageBundle.getString("appointment.fetched.success"));
            response.setResponseCode(ResponseEnum.APPT_FETCHED_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(appointments);
            logger.info("searchAppointments - Appointments fetched successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("search Appointments exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Search Appointment",
            notes = "This method will Search Appointment",
            produces = "application/json", nickname = "Search Appointment",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Search Appointment successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/patient/appointments/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> searchByPatient(HttpServletRequest request,
                                             @PathVariable("page") int page,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                             @RequestParam(value = "patientName") String patientName) {

        logger.error("Search Appointment API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("appointment.fetch.error"));
        response.setResponseCode(ResponseEnum.APPT_FETCHED_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {

            List<AppointmentWrapper> appointments = appointmentService.searchAppointmentByPatients(patientName, page, pageSize);
            //uncoment for pagination
           /* int countSearchedAppointments = appointmentService.countSearchedAppointmentsByPatient(patientName);
            logger.error("getAllPaginatedAppointments - fetched successfully");
            if (!HISCoreUtil.isListEmpty(appointments)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countSearchedAppointments > pageSize) {
                    int remainder = countSearchedAppointments % pageSize;
                    int totalPages = countSearchedAppointments / pageSize;
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
                returnValues.put("data", appointments);

                response.setResponseMessage(messageBundle.getString("appointment.fetched.success"));
                response.setResponseCode(ResponseEnum.APPT_FETCHED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(appointments);
                logger.info("searched Appointment Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }
            return new ResponseEntity<>(response, HttpStatus.OK);*/
            if (HISCoreUtil.isListEmpty(appointments)) {
                response.setResponseMessage(messageBundle.getString("appointment.search.not.found"));
                response.setResponseCode(ResponseEnum.APPT_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("searchAppointment - Appointment not found.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            response.setResponseMessage(messageBundle.getString("appointment.fetched.success"));
            response.setResponseCode(ResponseEnum.APPT_FETCHED_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(appointments);
            logger.info("searchAppointments - Appointments fetched successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("search Appointments exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "GET", value = "Fetch Appointment",
            notes = "This method will return Appointment on base of id",
            produces = "application/json", nickname = "Get Single Appointment",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Appointment found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAppointmentById(HttpServletRequest request,
                                                @PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("appointment.not-found"));
        response.setResponseCode(ResponseEnum.APPT_NOT_FOUND_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            AppointmentWrapper singleAppointment = this.appointmentService.getSingleAppointment(id);

            if (HISCoreUtil.isValidObject(singleAppointment)) {
                response.setResponseData(singleAppointment);
                response.setResponseCode(ResponseEnum.APPT_FOUND_SUCCESS.getValue());
                response.setResponseMessage(messageBundle.getString("appointment.found"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Appointment Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("appointment.not-found"));
                response.setResponseCode(ResponseEnum.APPT_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("Appointment Not Found ...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Appointment Not Found", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "PUT", value = "Update Appointment ",
            notes = "This method will Update Appointment",
            produces = "application/json", nickname = "Update Appointment",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Appointment successfully updated", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/update/room/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAppointmentRoom(HttpServletRequest request,
                                                   @PathVariable("id") long id,
                                                   @RequestParam(value = "status") String currentStatus) {

        logger.info("update Appointment API called...");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("appointment.update.error"));
        response.setResponseCode(ResponseEnum.APPT_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            Appointment alreadyExistAppointment = appointmentService.findById(id);
            if (HISCoreUtil.isValidObject(alreadyExistAppointment)) {
                logger.info("Appointment founded...");
                String savedAppointment = appointmentService.updateAppointmentRoom(currentStatus, alreadyExistAppointment);
                if (savedAppointment.equalsIgnoreCase("success")) {
                    logger.info("Appointment Updated...");
                    response.setResponseData(null);
                    response.setResponseMessage(messageBundle.getString("appointment.update.success"));
                    response.setResponseCode(ResponseEnum.APPT_UPDATE_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("Appointment updated successfully...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else if (savedAppointment.equalsIgnoreCase("already")) {
                    response.setResponseMessage(messageBundle.getString("appointment.already.exist"));
                    response.setResponseCode(ResponseEnum.APPT_ALREADY_EXISTS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("Appointment Already Exists...");

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                logger.info("Appointment not found...");
                response.setResponseMessage(messageBundle.getString("appointment.not-found"));
                response.setResponseCode(ResponseEnum.APPT_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Appointment not updated...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception ex) {
            logger.error("Update Appointment Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete Appointment ",
            notes = "This method will Delete Appointment on base of id",
            produces = "application/json", nickname = "Appointment Delete ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Appointment Deleted successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAppointment(HttpServletRequest request,
                                               @PathVariable("id") long id) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("appointment.delete.error"));
        response.setResponseCode(ResponseEnum.APPT_NOT_DELETED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            Appointment dbAppointment = this.appointmentService.findById(id);

            if (HISCoreUtil.isValidObject(dbAppointment)) {
                appointmentService.deleteAppointment(dbAppointment);
               // dbAppointment.setActive(false);


                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("appointment.delete.success"));
                response.setResponseCode(ResponseEnum.APPT_DELETE_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("Appointment Deleted successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("appointment.not-found"));
                response.setResponseCode(ResponseEnum.APPT_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());

                logger.info("Unable to find Appointment...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Unable to delete Appointment.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "GET", value = "Doctor Medical Services",
            notes = "This method will return Paginated Medical Services",
            produces = "application/json", nickname = "Paginated CMedical Services",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = " Doctors's  Medical Services for appointment fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/doctor/services", method = RequestMethod.GET)
    public ResponseEntity<?> getAllMedicalServicesWithDoctors(HttpServletRequest request) {

        logger.error("getAllMedicalServices against doctors for appointment API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            logger.error("getAllMedicalServices - Medical Services fetching from DB");
            List<MedicalServicesDoctorWrapper> mss = appointmentService.getMedicalServicesAgainstDoctors();

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


}
