package com.zainimtiaz.nagarro.controller.setting;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.service.CurrencyService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.CurrencyWrapper;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

/**
 * Created by jamal on 10/29/2018.
 */
@RestController
@RequestMapping("/setting/currency/")
public class CurrencyAPI {

    private Logger logger = LoggerFactory.getLogger(CurrencyAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");


    @Autowired
    private CurrencyService currencyService;

    @ApiOperation(httpMethod = "POST", value = "Save currency",
            notes = "This method will Save currency",
            produces = "application/json", nickname = "Save currency",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save currency successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseEntity<?> saveCurrency(@RequestBody CurrencyWrapper currencyWrapper) {

        logger.info("saveCurrency API Called...!!");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            if (currencyWrapper.getIos().isEmpty() || currencyWrapper.getIos() == "") {
                logger.warn("saveCurrency IOS is empty.....");
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseCode(ResponseEnum.CURRENCY_SAVE_IOS_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("currency.ios.required"));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (currencyWrapper.getSymbol() == "" || currencyWrapper.getSymbol().isEmpty()) {
                logger.warn("saveCurrency IOS symbol required.");
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseCode(ResponseEnum.CURRENCY_SAVE_SYMBOL_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("currency.symbol.required"));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (this.currencyService.getCurrencyByIOS(currencyWrapper.getIos())) {
                logger.warn("saveCurrency IOS duplicate.");
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseCode(ResponseEnum.CURRENCY_SAVE_IOS_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("currency.ios.duplicate"));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            this.currencyService.saveCurrency(currencyWrapper);
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseCode(ResponseEnum.CURRENCY_SAVE_SUCCESS.getValue());
            response.setResponseMessage(messageBundle.getString("currency.save.success"));
            logger.error("saveCurrency saved successfully..");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("saveCurrency exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Get paginated currencies ",
            notes = "This method will Get paginated  currencies",
            produces = "application/json", nickname = "Get paginated  currencies",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get paginated  currencies successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaginatedCurrencies(@PathVariable("page") int page,
                                                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {


        logger.info("getPaginatedCurrencies API Called...!!");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            Pageable pageable = new PageRequest(page, pageSize);
            List<CurrencyWrapper> currencyWrappers = this.currencyService.getPaginatedCurrencies(pageable);
            int count = this.currencyService.countPaginatedCurrencies();

            if (!HISCoreUtil.isListEmpty(currencyWrappers)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (count > pageSize) {
                    int remainder = count % pageSize;
                    int totalPages = count / pageSize;
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
                returnValues.put("data", currencyWrappers);

                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseCode(ResponseEnum.CURRENCY_PAGINATED_SUCCESS.getValue());
                response.setResponseMessage(messageBundle.getString("currency.paginated.success"));
                response.setResponseData(returnValues);
                logger.error("getPaginatedCurrencies  successfully..");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.CURRENCY_PAGINATED_ERROR.getValue());
            response.setResponseMessage(messageBundle.getString("currency.paginated.empty"));
            logger.error("getPaginatedCurrencies  empty list.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getPaginatedCurrencies exception...", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Get currency by id ",
            notes = "This method will Get currency by id",
            produces = "application/json", nickname = "Get currency by id",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get currency by id successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrencyById(@RequestParam("currencyId") int currencyId) {

        logger.info("getCurrencyById API Called...!!");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            if (currencyId <= 0) {
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseCode(ResponseEnum.CURRENCY_GET_ID_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("currency.id.required"));
                logger.error("getCurrencyById  successfully..");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setResponseData(this.currencyService.getCurrencyById(currencyId));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseCode(ResponseEnum.CURRENCY_GET_SUCCESS.getValue());
                response.setResponseMessage(messageBundle.getString("currency.get.success"));
                logger.error("getCurrencyById currency found successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception ex) {
            logger.error("getCurrencyById exception....", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Save currency",
            notes = "This method will Save currency",
            produces = "application/json", nickname = "Save currency",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save currency successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCurrency(@RequestBody CurrencyWrapper currencyWrapper) {

        logger.info("updateCurrency API Called...!!");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (currencyWrapper.getId() <= 0) {
                logger.warn("updateCurrency IOS is empty...");
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseCode(ResponseEnum.CURRENCY_UPDATE_ID_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("currency.id.required"));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (currencyWrapper.getIos().isEmpty() || currencyWrapper.getIos() == "") {
                logger.warn("updateCurrency IOS is empty.....");
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseCode(ResponseEnum.CURRENCY_SAVE_IOS_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("currency.ios.required"));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (currencyWrapper.getSymbol() == "" || currencyWrapper.getSymbol().isEmpty()) {
                logger.warn("updateCurrency IOS symbol required.");
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseCode(ResponseEnum.CURRENCY_SAVE_SYMBOL_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("currency.symbol.required"));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (this.currencyService.getCurrencyByIOSAndIdNot(currencyWrapper.getId(), currencyWrapper.getIos())) {
                logger.warn("updateCurrency IOS duplicate.");
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseCode(ResponseEnum.CURRENCY_SAVE_IOS_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("currency.ios.duplicate"));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            this.currencyService.updateCurrency(currencyWrapper);
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseCode(ResponseEnum.CURRENCY_UPDATE_SUCCESS.getValue());
            response.setResponseMessage(messageBundle.getString("currency.update.success"));
            logger.error("updateCurrency updated successfully..");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("updateCurrency exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Get system default currency",
            notes = "This method will get only default currency",
            produces = "application/json", nickname = "Get system default currency",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get system default currency successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "defaultCurrency", method = RequestMethod.DELETE)
    public ResponseEntity<?> getDefaultCurrencyWrapper() {
        logger.info("getDefaultCurrencyWrapper API - Init.........");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            response.setResponseData(this.currencyService.getDefaultCurrencyWrapper());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseCode(ResponseEnum.CURRENCY_GET_DEFAULT_SUCCESS.getValue());
            response.setResponseMessage(messageBundle.getString("currency.get.default.success"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            logger.error("getDefaultCurrencyWrapper API - Exception to get default currency.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete currency",
            notes = "This method will Delete currency",
            produces = "application/json", nickname = "Delete currency ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted currency successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCurrency(@RequestParam("currencyId") long currencyId) {
        logger.info("deleteCurrency API - Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (currencyId <= 0) {
                response.setResponseMessage(messageBundle.getString("currency.id.required"));
                response.setResponseCode(ResponseEnum.CURRENCY_DELETE_ID_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);
                logger.error("deleteCurrency API - Please select proper record,id not available.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            /*if (this.currencyService.hasChild(currencyId)) {
            response.setResponseMessage(messageBundle.getString("service.tax.delete.has.child"));
            response.setResponseCode(ResponseEnum.SERVICE_TAX_DELETE_HAS_CHILD.getValue());
            response.setResponseStatus(ResponseEnum.WARN.getValue());
            response.setResponseData(null);
            logger.info("deleteCurrency API - tax has child record. First delete its child record then you can delete it.");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }*/

            this.currencyService.deleteCurrency(currencyId);
            response.setResponseMessage(messageBundle.getString("currency.delete.success"));
            response.setResponseCode(ResponseEnum.CURRENCY_DELETE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);
            logger.info("deleteCurrency API - Deleted Successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("deleteCurrency API - deleted failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
