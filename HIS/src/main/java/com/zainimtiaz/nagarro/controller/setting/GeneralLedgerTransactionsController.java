package com.zainimtiaz.nagarro.controller.setting;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.service.GeneralLedgerTransactionsService;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ResourceBundle;

@RestController
@RequestMapping("/GLTController")
public class GeneralLedgerTransactionsController {
    @Autowired
    GeneralLedgerTransactionsService generalLedgerTransactionsService;
    private final Logger logger = LoggerFactory.getLogger(GeneralLedgerTransactionsController.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "GET", value = "Get All General Ledger Transactions Data",
            notes = "This method will get All General Ledger Transactions Data",
            produces = "application/json", nickname = "Get All General Ledger Transactions Data",
            response = GenericAPIResponse.class, protocols = "https")

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getAllGeneralLedgerTransactionsData() {
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            response.setResponseData(generalLedgerTransactionsService.getAll());
            response.setResponseMessage(messageBundle.getString("gl.transactions.fetched.success"));
            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("General Ledger Transactions data fetch successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("General Ledger Transactions List data fetch failed.", ex.fillInStackTrace());
            System.out.println("Exception : " + ex.getStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("gl.transactions.fetch.error"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
