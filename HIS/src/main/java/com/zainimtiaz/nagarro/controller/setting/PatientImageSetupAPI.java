package com.zainimtiaz.nagarro.controller.setting;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.PatientImageSetup;
import com.zainimtiaz.nagarro.service.PatientImageService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;




@RestController
@RequestMapping("/PatientImage")
public class PatientImageSetupAPI {

    @Autowired
    PatientImageService patientImageService;

    private final Logger logger = LoggerFactory.getLogger(PatientImageSetupAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "POST", value = "Save Patient Image Setup API ",
            notes = "This method will save Patient Image Setup",
            produces = "application/json", nickname = "Save Patient Image Setup",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/savePatientImageSetup", method = RequestMethod.POST)
    public ResponseEntity<?> saveSetup(HttpServletRequest request, @RequestBody PatientImageSetup imageSetupRequestWrapper) {

        logger.error("Save  Setup  API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            patientImageService.save(imageSetupRequestWrapper);
            response.setResponseData(patientImageService.getAll());
            response.setResponseMessage(messageBundle.getString("patient.image.setup.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Setup Configuration save successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Setup  Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("patient.image.setup.success.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "GET", value = "get Setup",
            notes = "This method will get  Setup ",
            produces = "application/json", nickname = "Get  Setup ",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/getSetup", method = RequestMethod.GET)
    public ResponseEntity<?> getSetup(){
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            response.setResponseData(patientImageService.getAll());


            response.setResponseMessage(messageBundle.getString("patient.image.fetched.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Setup data fetch successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Setup data  not fetched successfully/ Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("patient.image.setup.success.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "DELETE", value = "DELETE",
            notes = "This method will Delete",
            produces = "application/json", nickname = "Delete",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Delete  successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{imgId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImage(HttpServletRequest request,
                                               @PathVariable("imgId")  long  imgId) {

        logger.error("Delete   API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("patient.image.delete.error"));
        response.setResponseCode(ResponseEnum.ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("Delete   -  fetching from DB for existence");

            if (imgId <= 0) {
                response.setResponseMessage(messageBundle.getString("patient.image.delete.error"));
                response.setResponseCode(ResponseEnum.ERROR.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);

                logger.error("delete - Please provide proper Id");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            patientImageService.deleteImage(imgId);
            response.setResponseMessage(messageBundle.getString("patient.image.delete.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);

            logger.error("Delete  - deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("Delete  exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "PUT", value = "Update ",
            notes = "This method will Update ",
            produces = "application/json", nickname = "Update",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Updated successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAction(HttpServletRequest request,
                                               @RequestBody PatientImageSetup patientImageSetup) {


        logger.info("Update  - Update  id:" );
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("patient.image.setup.update.error"));
        response.setResponseCode(ResponseEnum.PAYMENTTYPE_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (HISCoreUtil.isNull(patientImageSetup.getCode()) || patientImageSetup.getId()<= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.info("update  - parameter is insufficient...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            patientImageService.updateImage(patientImageSetup);
            response.setResponseMessage(messageBundle.getString("patient.image.setup.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);
            logger.info("update  API -  updated successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("update    API - Exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
