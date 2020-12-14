package com.zainimtiaz.nagarro.controller.setting;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.LabTestSpeciman;
import com.zainimtiaz.nagarro.service.BulkImportService;
import com.zainimtiaz.nagarro.service.LabTestSpecimanService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/labTest")
public class LabTestSpecimanController {

    @Autowired
    LabTestSpecimanService labTestSpecimanService;
    @Autowired
    BulkImportService bulkImportService;

    private final Logger logger = LoggerFactory.getLogger(SmsTemplateController.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");


    @ApiOperation(httpMethod = "GET", value = "get lab test speciman Configurations",
            notes = "This method will get lab test speciman Configurations",
            produces = "application/json", nickname = "Get All lab test speciman Configurations",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTestSpeciman(){
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            response.setResponseData(labTestSpecimanService.getAll());

            response.setResponseMessage(messageBundle.getString("lab.test.speciman.configuration.fetched.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Lab test speciman List data fetch successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Lab test speciman List data  not fetched successfully/ Failed.", ex.fillInStackTrace());
            System.out.println("Exception : " + ex.getStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("lab.test.speciman.configuration.fetch.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "POST", value = "Save lab test speciman Configuration",
            notes = "This method will save lab test speciman Configuration",
            produces = "application/json", nickname = "Save lab test speciman Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/saveLabTestSpeciman", method = RequestMethod.POST)
    public ResponseEntity<?> saveLabTestSpeciman(@RequestBody LabTestSpeciman labTestSpecimanRequestWrapper) {

        logger.error("Save lab test speciman Configuration API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            labTestSpecimanService.saveConfiguration(labTestSpecimanRequestWrapper);
            response.setResponseData(labTestSpecimanService.getAll());
            response.setResponseMessage(messageBundle.getString("lab.test.speciman.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Lab test speciman Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Lab test speciman Configuration Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("lab.test.speciman.configuration.update.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Import Lab Test Data",
            notes = "This method will import lab tests' data",
            produces = "application/json", nickname = "Import Lab Test",
            response = GenericAPIResponse.class, protocols = "https")
    @RequestMapping(value = "/importRecords", method = RequestMethod.POST)
    public ResponseEntity<?> importLabTestRecords(@RequestParam("dataFile") MultipartFile dataFile ) {

        logger.info("importLabTestRecords API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            String fileName = dataFile.getOriginalFilename();
            File file = HISCoreUtil.multipartToFile(dataFile);
            int records = bulkImportService.importLabTestRecords(fileName);

            if (records > 0) {
                response.setResponseMessage(messageBundle.getString("lab.test.records.import.success"));
            } else {
                response.setResponseMessage(messageBundle.getString("lab.test.no.records.import"));
            }

            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info(records + " - Lab Test Specimen records imported successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (FileNotFoundException fnfe) {
            logger.error("importLabTestRecords File not found.", fnfe.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("lab.test.records.import.file.not.found"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            logger.error("importLabTestRecords Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("lab.test.records.import.failed"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "DELETE", value = "DELETE Drug",
            notes = "This method will DELETE Drug",
            produces = "application/json", nickname = "DELETE Drug",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "DELETE Drug  successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSpeciman(HttpServletRequest request,
                                        @RequestParam("specimanId") long specimanId) {

        logger.error("deleteDrug API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {

            if (specimanId <= 0) {
                response.setResponseMessage(messageBundle.getString("lab.delete.id.required"));
                response.setResponseCode(ResponseEnum.CLI_DPT_DELETE_DEPART_ID.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);

                logger.error("delete - Please provide proper sepeciman id.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }



            labTestSpecimanService.delete(specimanId);
            response.setResponseMessage(messageBundle.getString("lab.delete.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);

            logger.error("delete - deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("deleteDrug exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "POST", value = "Update",
            notes = "This method will Update .",
            produces = "application/json", nickname = "Update ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Update  successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSpeciman(HttpServletRequest request,
                                        @RequestBody LabTestSpeciman labTestSpecimanRequestWrapper) {
        logger.info("update API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            if (labTestSpecimanRequestWrapper.getId() <= 0) {
                response.setResponseMessage(messageBundle.getString("lab.update.id.required"));
                response.setResponseCode(ResponseEnum.ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateDrug API - Required Id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

           /*


            if (this.labTestSpecimanService.isNameDuplicateByNameAndNotEqualId(labTestSpecimanRequestWrapper.getId(), labTestSpecimanRequestWrapper.getTestName())) {
                response.setResponseMessage(messageBundle.getString("lab.update.name.duplicate"));
                response.setResponseCode(ResponseEnum.DRUG_UPDATE_NAME_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("updateDrug API - successfully saved.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }*/
            this.labTestSpecimanService.update(labTestSpecimanRequestWrapper);
            response.setResponseMessage(messageBundle.getString("lab.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("update API - successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("update exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
