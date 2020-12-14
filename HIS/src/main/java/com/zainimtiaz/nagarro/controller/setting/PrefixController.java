package com.zainimtiaz.nagarro.controller.setting;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.service.PrefixServices;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.wrapper.PrefixWrapper;
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

import java.util.ResourceBundle;

@RestController
@RequestMapping("/prefixConfiguration")
public class PrefixController {

    @Autowired
    PrefixServices prefixServices;

    private final Logger logger = LoggerFactory.getLogger(SmsTemplateController.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "GET", value = "get prefix Configurations",
            notes = "This method will get prefix Configurations",
            produces = "application/json", nickname = "Get All prefix Configurations",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getPrefixConfigurations(){
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            response.setResponseData(prefixServices.getAll());

            response.setResponseMessage(messageBundle.getString("prefix.configuration.fetched.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Prefix List data fetch successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Prefix List data  not fetched successfully/ Failed.", ex.fillInStackTrace());
            System.out.println("Exception : " + ex.getStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("prefix.configuration.fetch.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @ApiOperation(httpMethod = "POST", value = "Save Prefix Configuration",
            notes = "This method will Save Prefix Configuration",
            produces = "application/json", nickname = "Prefix Configuration",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/savePrefixConfiguration", method = RequestMethod.POST)
    public ResponseEntity<?> savePrefixConfiguration(@RequestBody PrefixWrapper configurationRequestWrapper) {

        logger.error("Prefix Configuration API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try
        {
            prefixServices.saveConfiguration(configurationRequestWrapper);
            response.setResponseData(prefixServices.getAll());
            response.setResponseMessage(messageBundle.getString("prefix.configuration.update.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("Prefix Configuration update successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            logger.error("Prefix Configuration Update Process Failed!", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("prefix.configuration.update.error"));
            response.setErrorMessageData(ex.getLocalizedMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
