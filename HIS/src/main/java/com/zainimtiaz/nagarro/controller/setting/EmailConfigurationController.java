package com.zainimtiaz.nagarro.controller.setting;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.EmailConfiguration;
import com.zainimtiaz.nagarro.service.EmailConfigurationService;
import com.zainimtiaz.nagarro.utill.AmazonSESUtil;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/emailConfiguration")
public class EmailConfigurationController {

    @Autowired
    EmailConfigurationService emailConfigurationService;

    private final Logger logger = LoggerFactory.getLogger(EmailConfigurationController.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "POST", value = "Save SMTP Email Configuration",
            notes = "This method will Save Email Configuration",
            produces = "application/json", nickname = "Save Email Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/saveSMTP", method = RequestMethod.POST)
    public ResponseEntity<?> saveSMTPConfiguration(HttpServletRequest request, @RequestBody EmailConfiguration configurationRequestWrapper) {

        logger.error("save SMTP Configuration API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
           EmailConfiguration emailConfiguration = emailConfigurationService.saveSMTPSConfiguration(configurationRequestWrapper);
            if(HISCoreUtil.isValidObject(emailConfiguration)) {
                response.setResponseMessage(messageBundle.getString("email.configuration.save.success"));
                response.setResponseCode(ResponseEnum.SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("SMTP Email Configuration save successfully...");

            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("SMTP Email Configuration Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("email.configuration.save.exception"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Save AmazonSES Email Configuration",
            notes = "This method will Save AmazonSES Email Configuration",
            produces = "application/json", nickname = "Save AmazonSES Email Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/saveSES", method = RequestMethod.POST)
    public ResponseEntity<?> saveSESConfiguration(HttpServletRequest request, @RequestBody EmailConfiguration configurationRequestWrapper) {

        logger.error("save SES Configuration API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
           EmailConfiguration emailConfiguration =  emailConfigurationService.saveSESConfiguration(configurationRequestWrapper);
           if(HISCoreUtil.isValidObject(emailConfiguration)) {
               AmazonSESUtil.getInstance(true);
               response.setResponseMessage(messageBundle.getString("email.configuration.save.success"));
               response.setResponseCode(ResponseEnum.SUCCESS.getValue());
               response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
               logger.info("AmazonSES Email Configuration saved successfully...");
           }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("AmazonSES Email Configuration Save Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("email.configuration.save.exception"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //     return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(httpMethod = "GET", value = "get Email Configurations",
            notes = "This method will get Email Configurations",
            produces = "application/json", nickname = "Get All Email Configurations",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getEmailConfigurations(){
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            response.setResponseData(emailConfigurationService.getEmailConfigurations());

            response.setResponseMessage(messageBundle.getString("email.configuration.fetched.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("All Email List data fetch successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("All Email List data  not fetched successfully/ Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("email.configuration.fetch.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
