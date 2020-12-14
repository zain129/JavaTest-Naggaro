package com.zainimtiaz.nagarro.controller.setting;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.SMSTemplate;
import com.zainimtiaz.nagarro.service.SmsTemplateService;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/smsConfiguration")
public class SmsTemplateController {
    @Autowired
    SmsTemplateService smsTemplateService;

    private final Logger logger = LoggerFactory.getLogger(SmsTemplateController.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");



    @ApiOperation(httpMethod = "POST", value = "Save Sms Configuration",
            notes = "This method will Save Sms Configuration",
            produces = "application/json", nickname = "Sms Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/saveSmsConfiguration", method = RequestMethod.POST)
    public ResponseEntity<?> saveSmsConfiguration(HttpServletRequest request, @RequestBody SMSTemplate configurationRequestWrapper) {

        logger.error("save SMS Configuration API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            smsTemplateService.saveConfiguration(configurationRequestWrapper);
            response.setResponseMessage(messageBundle.getString("sms.configuration.save.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("SMS Configuration saved successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("SMS Configuration Save Process Failed!", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("sms.configuration.save.exception"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "DELETE", value = "delete SMS Configurations",
            notes = "This method will delete SMS Configurations",
            produces = "application/json", nickname = "Delete SMS Configurations",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/deleteSmsConfig", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSmsConfig(@RequestParam("id") long id){
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            smsTemplateService.delete(id);
            response.setResponseData(smsTemplateService.getAll());
            response.setResponseMessage(messageBundle.getString("sms.configuration.delete.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("SMS data delete successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("SMS  data  not delete successfully/ Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("sms.configuration.delete.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "get SMS Configurations",
            notes = "This method will get SMS Configurations",
            produces = "application/json", nickname = "Get All SMS Configurations",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getSMSConfigurations(){
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            response.setResponseData(smsTemplateService.getAll());

            response.setResponseMessage(messageBundle.getString("sms.configuration.fetched.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("All SMS List data fetch successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("All SMS List data  not fetched successfully/ Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("sms.configuration.fetch.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "GET SMS Configuration By Id",
            notes = "This method will GET SMS Configuration By Id",
            produces = "application/json", nickname = "Get SMS Configuration By Id",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/getSmsById", method = RequestMethod.GET)
    public ResponseEntity<?> getSmsById(@RequestParam("id") long id){
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            response.setResponseData(smsTemplateService.getById(id));
            response.setResponseMessage(messageBundle.getString("sms.configuration.fetched.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("SMS data fetch successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("SMS  data  not fetch successfully/ Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("sms.configuration.fetched.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
