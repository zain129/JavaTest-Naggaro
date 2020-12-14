package com.zainimtiaz.nagarro.controller.patient;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.service.DrugManufacturerService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.DrugManufacturerWrapper;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("/setting/drugManufacturer/")
public class DrugManufacturerAPI {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(DrugManufacturerAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private DrugManufacturerService drugManufacturerService;


    @ApiOperation(httpMethod = "GET", value = "All Drug Manufacturers",
            notes = "This method will returns all Drug Manufacturers ",
            produces = "application/json", nickname = "Drug Manufacturers",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Drug Manufacturers fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDrugManufacturers(HttpServletRequest request) {

        logger.error("get All Drug Manufacturers  API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("drug.manufacturer.fetch.error"));
        response.setResponseCode(ResponseEnum.DRUG_MANUFACTURER_FETCHED_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("ALL Drug Manufacturers  API - Drug Manufacturers  fetching from DB");
            List<DrugManufacturerWrapper> durgMakers = drugManufacturerService.getAllDrugManufacturer();

            if (HISCoreUtil.isListEmpty(durgMakers)) {
                response.setResponseMessage(messageBundle.getString("drug.manufacturer.not.found"));
                response.setResponseCode(ResponseEnum.DRUG_MANUFACTURER_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Drug Manufacturers API - Drug Manufacturers  not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("drug.manufacturer.fetched.success"));
            response.setResponseCode(ResponseEnum.DRUG_MANUFACTURER_FETCHED_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(durgMakers);

            logger.error("Drug Manufacturers API - Drug Manufacturers successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Drug Manufacturers   API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "All Drug Manufacturers",
            notes = "This method will returns all Drug Manufacturers ",
            produces = "application/json", nickname = "Drug Manufacturers",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Drug Manufacturers fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/getAllActive", method = RequestMethod.GET)
    public ResponseEntity<?> getAllActiveDrugManufacturers(HttpServletRequest request) {

        logger.error("get Active All Drug Manufacturers  API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("drug.manufacturer.fetch.error"));
        response.setResponseCode(ResponseEnum.DRUG_MANUFACTURER_FETCHED_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("ALL Active Drug Manufacturers  API - Drug Manufacturers  fetching from DB");
            List<DrugManufacturerWrapper> durgMakers = drugManufacturerService.getAllActiveDrugManufacturer();

            if (HISCoreUtil.isListEmpty(durgMakers)) {
                response.setResponseMessage(messageBundle.getString("drug.manufacturer.not.found"));
                response.setResponseCode(ResponseEnum.DRUG_MANUFACTURER_NOT_FOUND_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Active Drug Manufacturers API - Drug Manufacturers  not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            response.setResponseMessage(messageBundle.getString("drug.manufacturer.fetched.success"));
            response.setResponseCode(ResponseEnum.DRUG_MANUFACTURER_FETCHED_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(durgMakers);

            logger.error("Active Drug Manufacturers API - Drug Manufacturers successfully fetched.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Active Drug Manufacturers   API -  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
