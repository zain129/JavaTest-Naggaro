package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.Appointment;
import com.zainimtiaz.nagarro.service.AppointmentService;
import com.zainimtiaz.nagarro.service.DashboardService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.response.DashboardResponseWrapper;
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
import java.util.List;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/dashboard")
public class DashboardAPI {

    @Autowired
    private DashboardService dashboardService;
    @Autowired
    AppointmentService appointmentService;

    private final Logger logger = LoggerFactory.getLogger(DashboardAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "GET", value = "DashBoard",
            notes = "This method will return All Dashboard",
            produces = "application/json", nickname = "All Dashboard",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Dashboard  fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getDashboard(HttpServletRequest request) {

        logger.error("Dashboard API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("dashboard.fetch.error"));
        response.setResponseCode(ResponseEnum.DASHBOARD_FETCHED_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("get All Dashboard - dashboard fetching from DB");
            List<DashboardResponseWrapper> dashboard = dashboardService.getDoctorDashboard();
            logger.error("getAlldashboard - dashboard fetched successfully");

            if (HISCoreUtil.isListEmpty(dashboard)) {
                response.setResponseMessage(messageBundle.getString("dashboard.not-found"));
                response.setResponseCode(ResponseEnum.DASHBOARD_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getAllDashboard API - dashboard not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("dashboard.fetched.success"));
            response.setResponseCode(ResponseEnum.DASHBOARD_FETCHED_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(dashboard);

            logger.error("getAllDashboard API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAll Dashboard exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update satatus

    @ApiOperation(httpMethod = "PUT", value = "Update Status ",
            notes = "This method will Update Status",
            produces = "application/json", nickname = "Update Status",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Status successfully updated", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/changestatus/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStatus(HttpServletRequest request,
                                          @PathVariable("id") String apptId,
                                          @RequestParam(value = "status" ) Long currentStatus) {

        logger.info("update Status API called...");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("status.update.error"));
        response.setResponseCode(ResponseEnum.STATUS_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            Appointment alreadyExistAppointment = appointmentService.findByNaturalId(apptId);
            if (HISCoreUtil.isValidObject(alreadyExistAppointment)) {
                logger.info("Appointment founded...");
                boolean changedStatus = appointmentService.changeStatus(currentStatus, alreadyExistAppointment);
                if (changedStatus) {
                    logger.info(" Appt Satatus Updated...");
                    response.setResponseData(null);
                    response.setResponseMessage(messageBundle.getString("appointment.update.success"));
                    response.setResponseCode(ResponseEnum.STATUS_UPDATE_SUCCESS.getValue());
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.info("Appointment Status updated successfully...");

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

}


