package com.zainimtiaz.nagarro.controller.patient;

import com.zainimtiaz.nagarro.enums.ResponseEnum;
import com.zainimtiaz.nagarro.model.Organization;
import com.zainimtiaz.nagarro.service.DocumentService;
import com.zainimtiaz.nagarro.service.OrganizationService;
import com.zainimtiaz.nagarro.utill.DateTimeUtil;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.DocumentWrapper;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;
import java.util.stream.IntStream;


@RestController
@RequestMapping("/patient/document")
public class DocumentAPI {

    private final Logger logger = LoggerFactory.getLogger(DocumentAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");
    @Autowired
    private DocumentService documentService;
    @Autowired
    private OrganizationService organizationService;

    @ApiOperation(httpMethod = "POST", value = "Save Document",
            notes = "This method will save the Document.",
            produces = "application/json", nickname = "Save Document",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Save Document successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveDocument(HttpServletRequest request,
                                          @RequestPart("myObject") DocumentWrapper documentWrapper,
                                          @RequestPart(name = "img", required = false) MultipartFile image) {
        logger.info("saveDocument API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (documentWrapper != null) {
                if (image != null) {
                    documentWrapper.setImage(image.getBytes());
                }
                if (documentWrapper.getPatientId() <= 0) {
                    response.setResponseCode(ResponseEnum.DOCUMENT_SAVE_PATIENT_REQUIRED.getValue());
                    response.setResponseMessage(messageBundle.getString("document.patient.required"));
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.error("saveDocument API - patient required");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                if (this.documentService.isNameDocumentAvailableByPatientId(documentWrapper.getName(), documentWrapper.getPatientId())) {
                    response.setResponseCode(ResponseEnum.DOCUMENT_SAVE_NAME_DUBPLUCATE.getValue());
                    response.setResponseMessage(messageBundle.getString("document.save.name.duplicate"));
                    response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                    logger.error("saveDocument API - name of document duplicate.");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                this.documentService.saveDocument(documentWrapper);
                response.setResponseCode(ResponseEnum.DOCUMENT_SAVE_SUCCESS.getValue());
                response.setResponseMessage(messageBundle.getString("document.save.success"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("saveDocument API - successfully saved.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }


            response.setResponseCode(ResponseEnum.DOCUMENT_SAVE_PATIENT_REQUIRED.getValue());
            response.setResponseMessage(messageBundle.getString("document.update.id.required"));
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("saveDocument API - successfully saved.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("saveDocument exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(httpMethod = "GET", value = "GET Paginated documents",
            notes = "This method will return Paginated  documents",
            produces = "application/json", nickname = "GET Paginated  documents",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated  documents fetched successfully.", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaginatedDocumentation(HttpServletRequest request,
                                                       @PathVariable("page") int page,
                                                       @RequestParam("patientId") String patientId,
                                                       @RequestParam(value = "pageSize",
                                                               required = false, defaultValue = "10") int pageSize) {

        logger.error("getPaginatedDocumentation API initiated");
        GenericAPIResponse response = new GenericAPIResponse();

        try {

            logger.error("getPaginatedDocumentation -  fetching from DB");
            if (patientId == null || Long.valueOf(patientId) <= 0) {
                response.setResponseCode(ResponseEnum.DOCUMENT_SAVE_PATIENT_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("document.patient.required"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("getPaginatedDocumentation API - patient required");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            Pageable pageable = new PageRequest(page, pageSize);
            List<DocumentWrapper> documentWrappers = this.documentService.getPaginatedDocuments(pageable, Long.valueOf(patientId));
            int documentWrappersCount = documentService.countPaginatedDocuments();
            Organization dbOrganization=organizationService.getAllOrgizationData();
            String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
            String systemDateFormat=dbOrganization.getDateFormat();
            String systemTimeFormat=dbOrganization.getTimeFormat();
            String hoursFormat = dbOrganization.getHoursFormat();
            if(hoursFormat.equals("12")){
                if(systemTimeFormat.equals("hh:mm")){
                    systemTimeFormat="hh:mm";
                }else{
                    systemTimeFormat="hh:mm:ss";
                }
            }else{
                if(systemTimeFormat.equals("hh:mm")){
                    systemTimeFormat="HH:mm";
                }else{
                    systemTimeFormat="HH:mm:ss";
                }
            }
            String standardFormatDateTime=systemDateFormat+" "+systemTimeFormat;
            for(int i=0;i<documentWrappers.size();i++){
                Date fromDte= null;
                try {
                    fromDte = DateTimeUtil.getDateFromString(documentWrappers.get(i).getUpdatedOn(), HISConstants.DATE_FORMAT_APP);
                    String readDateFrom= HISCoreUtil.convertDateToTimeZone(fromDte,standardFormatDateTime,Zone);
                    documentWrappers.get(i).setCreatedOn(readDateFrom);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

           //     Date updatedDate= DateTimeUtil.getDateFromString(readDateFrom,standardFormatDateTime);
           //     if(systemDateFormat!=null || !systemDateFormat.equals("")){
           //         String  dtelFrom=HISCoreUtil.convertDateToStringWithDateDisplay(updatedDate,standardFormatDateTime);
           //         documentWrappers.get(i).setUpdatedOn(dtelFrom);

                }



            logger.error("getPaginatedDocumentation - fetched successfully");

            if (!HISCoreUtil.isListEmpty(documentWrappers)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (documentWrappersCount > pageSize) {
                    int remainder = documentWrappersCount % pageSize;
                    int totalPages = documentWrappersCount / pageSize;
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
                returnValues.put("data", documentWrappers);

                response.setResponseMessage(messageBundle.getString("document.paginated.success"));
                response.setResponseCode(ResponseEnum.DOCUMENT_PAGINATED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);

                logger.error("getPaginatedDocumentation API successfully executed.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getPaginatedDocumentation exception..", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(httpMethod = "POST", value = "Get Document",
            notes = "This method will Get the Document.",
            produces = "application/json", nickname = "Get Document",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get Document successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/get", method = RequestMethod.GET)//, consumes = "multipart/form-data"
    public ResponseEntity<?> getDocumentById(HttpServletRequest request,
                                             @RequestParam("documentId") int documentId) {
        logger.info("getDocumentById API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (documentId <= 0) {
                response.setResponseMessage(messageBundle.getString("document.get.id.required"));
                response.setResponseCode(ResponseEnum.DOCUMENT_GET_ERROR.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("getDocumentById API - successfully saved.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setResponseData(this.documentService.getDocument(documentId));
            response.setResponseMessage(messageBundle.getString("document.get.success"));
            response.setResponseCode(ResponseEnum.DOCUMENT_GET_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("getDocumentById API - successfully saved.");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getDocumentById exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(httpMethod = "POST", value = "Update Document",
            notes = "This method will Update the Document.",
            produces = "application/json", nickname = "Update Document",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Update Document successfully ", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/update", method = RequestMethod.POST)//, consumes = "multipart/form-data"
    public ResponseEntity<?> updateDocument(HttpServletRequest request,
                                            @RequestPart("myObject") DocumentWrapper documentWrapper,
                                            @RequestPart(name = "img", required = false) MultipartFile image) {
        logger.info("updateDocument API - initiated..");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            if (documentWrapper.getPatientId() <= 0) {
                response.setResponseCode(ResponseEnum.DOCUMENT_SAVE_PATIENT_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("document.patient.required"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("updateDocument API - successfully saved.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            if (documentWrapper.getId() <= 0) {
                response.setResponseCode(ResponseEnum.DOCUMENT_UPDATE_ID_REQUIRED.getValue());
                response.setResponseMessage(messageBundle.getString("document.update.id.required"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("updateDocument API - successfully saved.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            if (this.documentService.isNameDocumentAvailableAgainstDocumentIdAndPatientId(documentWrapper.getName(), documentWrapper.getId(), documentWrapper.getPatientId())) {
                response.setResponseCode(ResponseEnum.DOCUMENT_SAVE_NAME_DUBPLUCATE.getValue());
                response.setResponseMessage(messageBundle.getString("document.save.name.duplicate"));
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.error("updateDocument API - name of document was duplicate.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            this.documentService.updateDocument(documentWrapper);
            response.setResponseMessage(messageBundle.getString("document.update.success"));
            response.setResponseCode(ResponseEnum.DOCUMENT_UPDATE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            logger.error("updateDocument API - successfully saved.");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateDocument exception.", e.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete Document",
            notes = "This method will Delete the Document",
            produces = "application/json", nickname = "Delete Document ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted Document successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{documentId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDocumentById(HttpServletRequest request,
                                                @PathVariable("documentId") long documentId) {
        logger.info("deleteDocumentById API - Called..");
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("document.delete.error"));
        response.setResponseCode(ResponseEnum.PATIENT_DELETE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);


        try {
            if (documentId <= 0) {
                response.setResponseMessage(messageBundle.getString("document.delete.id.required"));
                response.setResponseCode(ResponseEnum.DOCUMENT_DELETE_ERR.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("deleteDocumentById API - insufficient params.");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            this.documentService.deleteDocument(documentId);
            response.setResponseMessage(messageBundle.getString("document.delete.success"));
            response.setResponseCode(ResponseEnum.DOCUMENT_DELETE_SUCCESS.getValue());
            response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
            response.setResponseData(null);
            logger.info("deleteDocumentById API - Deleted Successfully...");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("deleteDocumentById API - deleted failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
