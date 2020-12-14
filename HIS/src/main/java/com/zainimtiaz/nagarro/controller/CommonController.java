package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.enums.DateFormatEnum;
import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.Zone;
import com.zainimtiaz.nagarro.service.HISUtilService;
import com.zainimtiaz.nagarro.service.OrganizationService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;
@RestController
@RequestMapping("/common")
public class CommonController {

    Logger logger = LoggerFactory.getLogger(AppointmentAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private HISUtilService utilService;

    @Autowired
    private OrganizationService organizationService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "All Countries  fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDateFormat(HttpServletRequest request) {

        logger.error("get All States  API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("country.fetch.error"));
        response.setResponseCode(ResponseEnum.COUNTRY_FETCHED_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("ALL Country  API - Country  fetching from DB");
            List<DateFormatEnum> dateFormatEnumLst = utilService.getDateFormatList();

            if (HISCoreUtil.isListEmpty(dateFormatEnumLst)) {
                response.setResponseMessage(messageBundle.getString("dateformat.not.found"));
                response.setResponseCode(ResponseEnum.DATEFORMAT_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Date Format   - Date Format  not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("dateformat.fetched.success"));
            response.setResponseCode(ResponseEnum.DATEFORMAT_FETCHED_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(dateFormatEnumLst);

            logger.error("Date Format API - State successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Date Format   API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "All Timezone",
            notes = "This method will return all Timezone",
            produces = "application/json", nickname = "All Timezone",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Timezone fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/timezoneApi", method = RequestMethod.GET)
    public ResponseEntity<?> getAllzonesTime(HttpServletRequest request) {

        logger.error("getAll Organization API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("timezone.fetch.error"));
        response.setResponseCode(ResponseEnum.TIMEZONE_FETCH_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getAll Timezone API - Timezone fetching from DB");
            List<Zone> timezone = organizationService.getAllZone();
            if (HISCoreUtil.isListEmpty(timezone)) {
                response.setResponseMessage(messageBundle.getString("timezone.not-found"));
                response.setResponseCode(ResponseEnum.TIMEZONE_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Timezone API - Timezone not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("timezone.fetch.success"));
            response.setResponseCode(ResponseEnum.TIMEZONE_FETCH_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(timezone);

            logger.error("getAllTimezone API - Timezone successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllTimezone API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
