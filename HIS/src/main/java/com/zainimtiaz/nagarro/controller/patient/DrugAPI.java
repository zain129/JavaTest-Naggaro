package com.zainimtiaz.nagarro.controller.patient;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.Drug;
import com.zainimtiaz.nagarro.service.BulkImportService;
import com.zainimtiaz.nagarro.service.DrugManufacturerService;
import com.zainimtiaz.nagarro.service.DrugService;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.DrugWrapper;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by jamal on 10/22/2018.
 */
@RestController
@RequestMapping("/setting/drug/")
public class DrugAPI {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(DrugAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private DrugService drugService;
    @Autowired
    private DrugManufacturerService drugManufacturerService;
    @Autowired
    private BulkImportService bulkImportService;

    @ApiOperation(httpMethod = "GET", value = "Drug Natural Id",
            notes = "This method will return drug Natural Id",
            produces = "application/json", nickname = "GetNaturalId",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = " Drug Natural Id found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "natural", method = RequestMethod.GET)
    public ResponseEntity<?> getDrugNaturalId() {

        GenericAPIResponse response = new GenericAPIResponse();
        try {
            response.setResponseData(this.drugService.getDrugNaturalId());
            response.setResponseMessage(messageBundle.getString("drug.get.natural.success"));
            response.setResponseCode(ResponseEnum.DRUG_GET_NATURAL_ID_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("getDrugNaturalId Found successfully...");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getDrugNaturalId Exception..", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "POST", value = "Save Drug",
            notes = "This method will save the Drug.",
            produces = "application/json", nickname = "Save Drug",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Saved successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseEntity<?> saveDrug(HttpServletRequest request,
                                      @RequestBody DrugWrapper drugWrapper) {
        logger.info("saveDrug API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            if (drugWrapper.getDrugName().isEmpty() || drugWrapper.getDrugName().length() == 0) {
                response.setResponseCode(ResponseEnum.DRUG_SAVE_NAME_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("drug.save.name.required"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("saveDrug API - name of DRUG Required.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            if (this.drugService.isNameDrugDuplicateByName(drugWrapper.getDrugName())) {
                response.setResponseCode(ResponseEnum.DRUG_SAVE_NAME_DUBPLUCATE.getValue());
                response.setResponseMessage(messageBundle.getString("drug.save.name.duplicate"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("saveDrug API - name of DRUG duplicate.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            this.drugService.saveDrug(drugWrapper);
            response.setResponseCode(ResponseEnum.DRUG_SAVE_SUCCESS.getValue());
            response.setResponseMessage(messageBundle.getString("drug.save.success"));
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("saveDrug API - successfully saved.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("saveDrug exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Load all drugs",
            notes = "This method will save the Drug.",
            produces = "application/json", nickname = "Load all drug",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All drug loaded successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaginatedAllDrugs(@PathVariable("page") int page,
                                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        logger.info("getPaginatedAllDrugs API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {


            logger.error("getPaginatedAllDrugs - fetching from DB");
            List<DrugWrapper> drugWrappers = drugService.getPaginatedAllDrugs(new PageRequest(page, pageSize));
            int dptsCount = drugService.countPaginatedAllDrugs();

            if (!HISCoreUtil.isListEmpty(drugWrappers)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (dptsCount > pageSize) {
                    int remainder = dptsCount % pageSize;
                    int totalPages = dptsCount / pageSize;
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
                returnValues.put("data", drugWrappers);

                response.setResponseMessage(messageBundle.getString("drug.get.paginated.success"));
                response.setResponseCode(ResponseEnum.DRUG_GET_PAGINATED_SUCCESS.getValue());//
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getPaginatedAllDrugs API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getPaginatedAllDrugs exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
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
    public ResponseEntity<?> deleteDrug(HttpServletRequest request,
                                        @RequestParam("drugId") long drugId) {

        logger.error("deleteDrug API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {

            if (drugId <= 0) {
                response.setResponseMessage(messageBundle.getString("drug.delete.id.required"));
                response.setResponseCode(ResponseEnum.CLI_DPT_DELETE_DEPART_ID.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);

                logger.error("deleteDrug - Please provide proper department id.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

           /* if (this.drugService.hasChild(drugId)) {
                response.setResponseMessage(messageBundle.getString("cli.dpts.delete.has.child"));
                response.setResponseCode(ResponseEnum.CLI_DPT_DELETE_HAS_CHILD.getValue());
                response.setResponseStatus(ResponseEnum.WARN.getValue());
                response.setResponseData(null);

                logger.error("deleteDrug - department has child record so you cannot delete it. First , delete its child record then you can delete it");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }*/

            drugService.deleteDrug(drugId);
            response.setResponseMessage(messageBundle.getString("drug.delete.success"));
            response.setResponseCode(ResponseEnum.DRUG_DELETE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);

            logger.error("deleteDrug - deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("deleteDrug exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "GET Paginated Medications",
            notes = "This method will return Paginated  Medications",
            produces = "application/json", nickname = "GET Paginated Medications",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated  Medications fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> getDrugsByName(@RequestParam(value = "drugName", required = false) String drugName) {

        logger.error("getMedicationByNameAutocomplete API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {
            logger.error("getMedicationByNameAutocomplete -  fetching from DB");
           // Drug listObj= this.drugService.searchByDrugNameAutoCompleteDetail(drugName);
            response.setResponseData(this.drugService.searchByDrugNameAutoComplete(drugName));
      //      response.setResponseMessage(this.searchByDrugNameAutoCompleteDetail(drugName));

            response.setResponseCode(ResponseEnum.DRUG_GET_SUCCESS.getValue());//(""),
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());

            logger.error("getMedicationByNameAutocomplete API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getDrugByNameAutocomplete exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Drug Id",
            notes = "This method will return drug wrapper on base of id",
            produces = "application/json", nickname = "Get Single User",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = " Drug found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResponseEntity<?> getDrugById(HttpServletRequest request,
                                         @RequestParam("drugId") int id) {

        GenericAPIResponse response = new GenericAPIResponse();
        try {
            DrugWrapper drugWrapper = this.drugService.getDrugWrapper(id);
            Drug drug = this.drugService.getDrugById(id);
            Map<String, Object> drugMakerInfo = new HashMap<>();
            if (drug.getDrugManufacturer() != null) {
                drugMakerInfo.put("drugMakerId", drug.getDrugManufacturer().getId());
                drugMakerInfo.put("drugMaker", drug.getDrugManufacturer().getName());
                drugWrapper.setDrugMaker(drug.getDrugManufacturer().getName());
            } else {
                drugMakerInfo.put("drugMakerId", -1);
                drugMakerInfo.put("drugMaker", "Select Manufacturer");
                drugWrapper.setDrugMaker("Select Manufacturer");
            }
            drugWrapper.setDrugInfo(drug.getDruginfo());
            drugWrapper.setAddInfo(drugMakerInfo);

            response.setResponseData(drugWrapper);
            response.setResponseMessage(messageBundle.getString("drug.get.success"));
            response.setResponseCode(ResponseEnum.DRUG_GET_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("getDrugById Found successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getDrugById Exception", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Update patient Allergy",
            notes = "This method will Update the patient Allergy.",
            produces = "application/json", nickname = "Update patient Allergy",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Update patient Allergy successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDrug(HttpServletRequest request,
                                        @RequestBody DrugWrapper drugWrapper) {
        logger.info("updateDrug API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {

            if (drugWrapper.getId() <= 0) {
                response.setResponseMessage(messageBundle.getString("drug.update.id.required"));
                response.setResponseCode(ResponseEnum.DRUG_UPDATE_ID_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateDrug API - Required Id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (drugWrapper.getDrugName().trim().equals("")) {
                response.setResponseMessage(messageBundle.getString("drug.update.name.required"));
                response.setResponseCode(ResponseEnum.DRUG_UPDATE_NAME_REQUIRED.getValue());//DOC_SUC_13
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("updateDrug API - Required patient id ?.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (this.drugService.isNameDrugDuplicateByNameAndNotEqualId(drugWrapper.getId(), drugWrapper.getDrugName())) {
                response.setResponseMessage(messageBundle.getString("drug.update.name.duplicate"));
                response.setResponseCode(ResponseEnum.DRUG_UPDATE_NAME_REQUIRED.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("updateDrug API - successfully saved.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            this.drugService.updateDrug(drugWrapper);
            response.setResponseMessage(messageBundle.getString("drug.update.success"));
            response.setResponseCode(ResponseEnum.DRUG_UPDATE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("updateDrug API - successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateDrug exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(httpMethod = "GET", value = "Drug all",
            notes = "This method will return drug wrapper on base of all",
            produces = "application/json", nickname = "Get Single User",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = " all drugs found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDrugs() {

        GenericAPIResponse response = new GenericAPIResponse();
        try {
            Map<String, Object> returnValues = new LinkedHashMap<>();
            returnValues.put("data", this.drugService.getAllDrugWrappers());

            response.setResponseData(returnValues);
            response.setResponseMessage(messageBundle.getString("drug.get.success"));
            response.setResponseCode(ResponseEnum.DRUG_GET_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info("getAllDrugWrappers Found successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getAllDrugWrappers Exception", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "GET Paginated Medications",
            notes = "This method will return Paginated  Medications",
            produces = "application/json", nickname = "GET Paginated Medications",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated  Medications fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/searchName", method = RequestMethod.GET)
    public ResponseEntity<?> getDrugsByNameSearch(@RequestParam(value = "drugName", required = false) String drugName) {
        logger.error("getMedicationByNameAutocomplete API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            logger.error("getMedicationByNameAutocomplete -  fetching from DB");
            // Drug listObj= this.drugService.searchByDrugNameAutoCompleteDetail(drugName);
            response.setResponseData(this.drugService.searchByDrugNameAutoCompleteDetail(drugName));
            response.setResponseCode(ResponseEnum.DRUG_GET_SUCCESS.getValue());//(""),
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("getMedicationByNameAutocomplete API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getDrugByNameAutocomplete exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "GET Paginated Medications",
            notes = "This method will return Paginated  Medications",
            produces = "application/json", nickname = "GET Paginated Medications",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated  Medications fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/searchStrengths", method = RequestMethod.GET)
    public ResponseEntity<?> getDrugsByNameSearchStrengths(@RequestParam(value = "drugName", required = false) String drugName) {
        logger.error("getMedicationByNameAutocomplete API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            logger.error("getMedicationByNameAutocomplete -  fetching from DB");
            response.setResponseData(this.drugService.searchByDrugNameAutoCompleteStrengths(drugName));
            response.setResponseCode(ResponseEnum.DRUG_GET_SUCCESS.getValue());//(""),
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("getMedicationByNameAutocomplete API successfully executed.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("getDrugByNameAutocomplete exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "Import Drug Data",
            notes = "This method will import drugs' data",
            produces = "application/json", nickname = "Import Durgs",
            response = GenericAPIResponse.class, protocols = "https")
    @RequestMapping(value = "/importRecords", method = RequestMethod.POST)
    public ResponseEntity<?> importDrugRecords(@RequestParam("dataFile") MultipartFile dataFile ) {
        logger.info("importDrugRecords API initiated");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            String fileName = dataFile.getOriginalFilename();
            File file = HISCoreUtil.multipartToFile(dataFile);
            int records = bulkImportService.importDrugRecords(fileName);

            if (records > 0) {
                response.setResponseMessage(messageBundle.getString("drug.records.import.success"));
            } else {
                response.setResponseMessage(messageBundle.getString("drug.no.records.import"));
            }

            response.setResponseCode(ResponseEnum.SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.info(records + " - Drug records imported successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (FileNotFoundException fnfe) {
            logger.error("importDrugRecords File not found.", fnfe.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("drug.records.import.file.not.found"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            logger.error("importDrugRecords Process Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("drug.records.import.failed"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
