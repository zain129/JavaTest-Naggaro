package com.zainimtiaz.nagarro.controller.setting;

import com.amazonaws.util.DateUtils;
import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.Organization;
import com.zainimtiaz.nagarro.service.OrganizationService;
import com.zainimtiaz.nagarro.utill.DateTimeUtil;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.wrapper.TaxWrapper;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import com.zainimtiaz.nagarro.service.TaxService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
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
import java.util.*;
import java.util.stream.IntStream;

/*
 * @author    : jamal
 * @Date      : 7/31/2018.
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.controller.setting
 * @FileName  : TaxAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@RestController
@RequestMapping("/setting/tax")
public class TaxAPI {

    Logger logger = LoggerFactory.getLogger(TaxAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    TaxService taxService;
    @Autowired
    private OrganizationService organizationService;

    @ApiOperation(httpMethod = "GET", value = "All Services Tax",
            notes = "This method will return All Service Tax",
            produces = "application/json", nickname = "All Service Tax",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Service Tax fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTax(HttpServletRequest request) {

        logger.error("getAllServiceTax API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("service.tax.fetch.error"));
        response.setResponseCode(ResponseEnum.SERVICE_TAX_FETCH_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getAllServiceTax - service tax fetching from DB");
            List<TaxWrapper> taxes = taxService.findAllActiveTax();
            Organization dbOrganization=organizationService.getAllOrgizationData();
            String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
            String systemDateFormat=dbOrganization.getDateFormat();

            logger.error("getAllServiceTax - tax fetched successfully");

            if (HISCoreUtil.isListEmpty(taxes)) {
                response.setResponseMessage(messageBundle.getString("service.tax.not.found.error"));
                response.setResponseCode(ResponseEnum.MED_SERVICE_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getAllServiceTax API - Taxes not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setResponseMessage(messageBundle.getString("service.tax.fetch.success"));
            response.setResponseCode(ResponseEnum.SERVICE_TAX_FETCH_SUCCESS.getValue());
            response.setResponseStatus(systemDateFormat);
            response.setResponseData(taxes);

            logger.error("getAllServiceTax API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("getAllServiceTax exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "All Services Tax",
            notes = "This method will return All Service Tax",
            produces = "application/json", nickname = "All Service Tax",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All Service Tax fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/dataTable", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTaxesForDataTable() {

        logger.error("getAllTaxesForDataTable API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("service.tax.fetch.error"));
        response.setResponseCode(ResponseEnum.SERVICE_TAX_FETCH_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getAllTaxesForDataTable - service tax fetching from DB");
            List<TaxWrapper> taxes = taxService.getAllTaxesForDataTable();
            Organization dbOrganization=organizationService.getAllOrgizationData();
            String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
            //  Date dteFrom=new Date();
            //  Date dteTo=new Date();
            String systemDateFormat=dbOrganization.getDateFormat();
            String systemTimeFormat=dbOrganization.getTimeFormat();
            String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
            String standardFormatDateTime=systemDateFormat+" "+systemTimeFormat;
        //    System.out.println("Time"+currentTime);
            //  dte=problemWrapper.getDatePrescribedDate();
            for(int i=0;i<taxes.size();i++){

          //      LocalDate dteFromLocal=HISCoreUtil.convertToDateLocal(taxes.get(i).getFromDate());

            Date dteFrom=HISCoreUtil.convertStringDateObjectTax(taxes.get(i).getStrfromDate());
            Date dteTo=HISCoreUtil.convertStringDateObjectTax(taxes.get(i).getStrtoDate());
            String readDateFrom=HISCoreUtil.convertDateToTimeZone(dteFrom,"yyyy-MM-dd",Zone);
            String readDateTo=HISCoreUtil.convertDateToTimeZone(dteTo,"yyyy-MM-dd",Zone);
         //   Date scheduledDateFrom=HISCoreUtil.convertStringDateObject(readDateFrom);
         //   Date scheduledDateTo=HISCoreUtil.convertStringDateObject(readDateTo);
                Date fromDte= DateTimeUtil.getDateFromString(readDateFrom,"yyyy-MM-dd");
                Date toDte=DateTimeUtil.getDateFromString(readDateTo,"yyyy-MM-dd");
             if(systemDateFormat!=null || !systemDateFormat.equals("")){
                 String  dtelFrom=HISCoreUtil.convertDateToStringWithDateDisplay(fromDte,systemDateFormat);
                 taxes.get(i).setStrfromDate(dtelFrom);
                 String  dtelTo=HISCoreUtil.convertDateToStringWithDateDisplay(toDte,systemDateFormat);
                 taxes.get(i).setStrtoDate(dtelTo);
                 taxes.get(i).setFromDate(fromDte);
                 taxes.get(i).setToDate(toDte);
             }

            }
            logger.error("getAllServiceTax - tax fetched successfully");

            if (HISCoreUtil.isListEmpty(taxes)) {
                response.setResponseMessage(messageBundle.getString("service.tax.not.found.error"));
                response.setResponseCode(ResponseEnum.MED_SERVICE_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("getAllTaxesForDataTable API - Taxes not found");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setResponseMessage(messageBundle.getString("service.tax.fetch.success"));
            response.setResponseCode(ResponseEnum.SERVICE_TAX_FETCH_SUCCESS.getValue());
            response.setResponseStatus(systemDateFormat);
            response.setResponseData(taxes);

            logger.error("getAllTaxesForDataTable API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("getAllTaxesForDataTable exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "GET", value = "Paginated Services Tax",
            notes = "This method will return Paginated Service Tax",
            produces = "application/json", nickname = "Paginated Service Tax",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated Service Tax fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPaginatedServiceTax(HttpServletRequest request,
                                                       @PathVariable("page") int page,
                                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        logger.error("getAllPaginatedServiceTax API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("service.tax.fetch.error"));
        response.setResponseCode(ResponseEnum.SERVICE_TAX_FETCH_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            logger.error("getAllPaginatedServiceTax - service tax fetching from DB");
            List<TaxWrapper> taxes = taxService.findAllPaginatedTax(page, pageSize);
            int taxCount = taxService.countAllTax();

            logger.error("getAllPaginatedServiceTax - tax fetched successfully");

            if (!HISCoreUtil.isListEmpty(taxes)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (taxCount > pageSize) {
                    int remainder = taxCount % pageSize;
                    int totalPages = taxCount / pageSize;
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
                returnValues.put("data", taxes);

                response.setResponseMessage(messageBundle.getString("service.tax.fetch.success"));
                response.setResponseCode(ResponseEnum.SERVICE_TAX_FETCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getAllPaginatedServiceTax API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getAllPaginatedServiceTax exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete Service Tax",
            notes = "This method will Delete the Service Tax",
            produces = "application/json", nickname = "Delete Service Tax ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted Service Tax successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTax(HttpServletRequest request,
                                              @RequestParam("taxId") long taxId) {
        logger.info("deleteServiceTax API - Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (taxId <= 0) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("deleteServiceTax API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (taxService.hasChild(taxId)) {
                response.setResponseMessage(messageBundle.getString("service.tax.delete.has.child"));
                response.setResponseCode(ResponseEnum.SERVICE_TAX_DELETE_HAS_CHILD.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);
                logger.info("deleteServiceTax API - tax has child record. First delete its child record then you can delete it.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            taxService.deleteTax(taxId);
            response.setResponseMessage(messageBundle.getString("service.tax.delete.success"));
            response.setResponseCode(ResponseEnum.SERVICE_TAX_DELETE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);
            logger.info("deleteServiceTax API - Deleted Successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("deleteServiceTax API - deleted failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Save Service Tax",
            notes = "This method will save the service tax.",
            produces = "application/json", nickname = "Save Service Tax",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save Service Tax successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveTax(HttpServletRequest request,
                                     @RequestBody TaxWrapper taxWrapper) {
        logger.info("saveTax API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();

        try {

            if (HISCoreUtil.isNull(taxWrapper.getName())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);

                logger.error("saveTax API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            String  fromDate = HISCoreUtil.convertDateToString(taxWrapper.getFromDate(), HISConstants.DATE_FORMATE_YYY_MM_dd);
            String  toDate = HISCoreUtil.convertDateToString(taxWrapper.getToDate(), HISConstants.DATE_FORMATE_YYY_MM_dd);
            Date dteFrom =HISCoreUtil.convertToAPPDateZone(fromDate,"yyyy-MM-dd");

            Date dteToDate=HISCoreUtil.convertToAPPDateZone(toDate,"yyyy-MM-dd");
            taxWrapper.setFromDate(dteFrom);
            taxWrapper.setToDate(dteToDate);
            taxWrapper.setStrfromDate(fromDate);
            taxWrapper.setStrtoDate(toDate);
            if (dteFrom.after(dteToDate)) {
                response.setResponseMessage(messageBundle.getString("service.tax.from.date"));
                response.setResponseCode(ResponseEnum.SERVICE_TAX_FROM_DATE.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);

                logger.error("saveTax API - Tax FROM DATE should be less than or equal to 'TO DATE'.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (taxService.isAlreadyExist(taxWrapper)) {
                response.setResponseMessage(messageBundle.getString("service.tax.already.exist"));
                response.setResponseCode(ResponseEnum.SERVICE_TAX_ALREADY_EXIST_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);

                logger.error("saveTax API - Tax already exist with same name.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            taxService.saveTax(taxWrapper);
            response.setResponseMessage(messageBundle.getString("service.tax.save.success"));
            response.setResponseCode(ResponseEnum.SERVICE_TAX_SAVE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);

            logger.error("saveTax API - Tax successfully saved.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("saveTax exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "PUT", value = "Update Tax Service",
            notes = "This method will update Tax Service",
            produces = "application/json", nickname = "Update Tax Service",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tax service updated successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTax(HttpServletRequest request,
                                       @RequestBody TaxWrapper updateRequest) {
        logger.info("updateTax API initiated..");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            if (updateRequest.getId() <= 0 ||
                    HISCoreUtil.isNull(updateRequest.getStrfromDate()) ||
                    HISCoreUtil.isNull(updateRequest.getStrtoDate())) {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateTax API - insufficient params.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
      //      Date fromDate = HISCoreUtil.convertToDateWithTime(updateRequest.getStrfromDate(), HISConstants.DATE_FORMATE_YYY_MM_dd);
      //      Date toDate = HISCoreUtil.convertToDateWithTime(updateRequest.getStrtoDate(), HISConstants.DATE_FORMATE_YYY_MM_dd);

            String  fromDate = HISCoreUtil.convertDateToString(updateRequest.getFromDate(), HISConstants.DATE_FORMATE_YYY_MM_dd);
            String  toDate = HISCoreUtil.convertDateToString(updateRequest.getToDate(), HISConstants.DATE_FORMATE_YYY_MM_dd);
            Date dteFrom =HISCoreUtil.convertToAPPDateZone(fromDate,"yyyy-MM-dd");

            Date dteToDate=HISCoreUtil.convertToAPPDateZone(toDate,"yyyy-MM-dd");
            updateRequest.setFromDate(dteFrom);
            updateRequest.setToDate(dteToDate);
            updateRequest.setStrfromDate(fromDate);
            updateRequest.setStrtoDate(toDate);
            if (dteFrom.after(dteToDate)) {
                response.setResponseMessage(messageBundle.getString("service.tax.update.from.date"));
                response.setResponseCode(ResponseEnum.SERVICE_TAX_FROM_DATE.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);

                logger.error("updateTax API - Tax FROM DATE should be less than or equal to 'TO DATE'.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (taxService.isAlreadyExist(updateRequest)) {
                response.setResponseMessage(messageBundle.getString("service.tax.already.exist"));
                response.setResponseCode(ResponseEnum.SERVICE_TAX_ALREADY_EXIST_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);

                logger.error("updateICDCode API - Tax already exist with same name.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            taxService.updateTaxService(updateRequest);
            response.setResponseData(null);
            response.setResponseMessage(messageBundle.getString("service.tax.update.success"));
            response.setResponseCode(ResponseEnum.SERVICE_TAX_UPDATE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("updateICDCode API - update failed.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "GET Search Taxes",
            notes = "This method will return Searched Tax",
            produces = "application/json", nickname = "Searched Taxes ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Searched Taxes fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> searchTaxByName(HttpServletRequest request,
                                             @PathVariable("page") int pageNo,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                             @RequestParam(value = "searchTax") String searchTaxName) {

        logger.info("searchTaxByName initiated");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("icd.not-found"));
        response.setResponseCode(ResponseEnum.ICD_CODE_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<TaxWrapper> taxes = taxService.searchByTaxByName(searchTaxName, pageNo, pageSize);
            int taxesCount = taxService.countSearchByTaxByName(searchTaxName);

            if (!HISCoreUtil.isListEmpty(taxes)) {
                logger.info("searchTaxByName fetched from DB successfully...");
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (taxesCount > pageSize) {
                    int remainder = taxesCount % pageSize;
                    int totalPages = taxesCount / pageSize;
                    if (remainder > 0) {
                        totalPages = totalPages + 1;
                    }
                    pages = new int[totalPages];
                    pages = IntStream.range(0, totalPages).toArray();
                    currPage = pageNo;
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
                returnValues.put("data", taxes);

                response.setResponseMessage(messageBundle.getString("service.tax.fetched.success"));
                response.setResponseCode(ResponseEnum.SERVICE_TAX_SEARCH_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("All searched taxes fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("get all filtered Search Taxes failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
