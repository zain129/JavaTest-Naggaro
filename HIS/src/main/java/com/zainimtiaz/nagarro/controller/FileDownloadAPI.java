package com.zainimtiaz.nagarro.controller;

import com.zainimtiaz.nagarro.enums.InvoiceMessageEnum;
import com.zainimtiaz.nagarro.wrapper.GenericAPIResponse;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/fileDownload")
public class FileDownloadAPI {

    private final Logger logger = LoggerFactory.getLogger(FileDownloadAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @ApiOperation(httpMethod = "GET", value = "Download ICD Code Sample File",
            notes = "This method will Download ICD Code Sample File",
            produces = "application/json", nickname = "Download ICD Code Sample File",
            response = GenericAPIResponse.class, protocols = "https")
    @RequestMapping(value = "/icdCodeSample", method = RequestMethod.GET)
    public ResponseEntity<?> icdCodeSampleFileDownload() {
        logger.info("icdCodeSampleFileDownload method initialized successfully...");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            URL resource = getClass().getClassLoader().getResource("sample_files/icd.xlsx");
            if (resource == null) {
                logger.error("ICD Code sample file not found...");
                return this.sampleFileNotFound();
            }

            File file = new File(resource.getPath());
            if (!file.exists()) {
                logger.error("ICD Code sample file not found...");
                return this.sampleFileNotFound();
            }
            boolean readOnly = file.setReadOnly();

            this.openFile(file.getCanonicalPath());
            response.setResponseMessage(messageBundle.getString("sample.file.download.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("ICD Code sample file downloaded successfully...");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("ICD Code sample file download Failed...", ex.fillInStackTrace());
            return this.sampleFileDownloadFailed(ex);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Download Patient Sample File",
            notes = "This method will Download Patient File",
            produces = "application/json", nickname = "Download Patient Sample File",
            response = GenericAPIResponse.class, protocols = "https")
    @RequestMapping(value = "/patientSample", method = RequestMethod.GET)
    public ResponseEntity<?> patientSampleFileDownload() {
        logger.info("patientSampleFileDownload method initialized successfully...");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            URL resource = getClass().getClassLoader().getResource("sample_files/patient.xlsx");
            if (resource == null) {
                logger.error("Patient sample file not found...");
                return this.sampleFileNotFound();
            }

            File file = new File(resource.getPath());
            if (!file.exists()) {
                logger.error("Patient sample file not found...");
                return this.sampleFileNotFound();
            }
            boolean readOnly = file.setReadOnly();

            this.openFile(file.getCanonicalPath());
            response.setResponseMessage(messageBundle.getString("sample.file.download.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("Patient sample file downloaded successfully...");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Patient sample file download Failed...", ex.fillInStackTrace());
            return this.sampleFileDownloadFailed(ex);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Download Lab Test Sample File",
            notes = "This method will Download Lab Test File",
            produces = "application/json", nickname = "Download Lab Test Sample File",
            response = GenericAPIResponse.class, protocols = "https")
    @RequestMapping(value = "/labTestSample", method = RequestMethod.GET)
    public ResponseEntity<?> labTestSampleFileDownload() {
        logger.info("labTestSampleFileDownload method initialized successfully...");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            URL resource = getClass().getClassLoader().getResource("sample_files/lab.xlsx");
            if (resource == null) {
                logger.error("Lab Test sample file not found...");
                return this.sampleFileNotFound();
            }

            File file = new File(resource.getPath());
            if (!file.exists()) {
                logger.error("Lab Test sample file not found...");
                return this.sampleFileNotFound();
            }
            boolean readOnly = file.setReadOnly();

            this.openFile(file.getCanonicalPath());
            response.setResponseMessage(messageBundle.getString("sample.file.download.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("Lab Test sample file downloaded successfully...");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Lab Test sample file download Failed...", ex.fillInStackTrace());
            return this.sampleFileDownloadFailed(ex);
        }
    }

    @ApiOperation(httpMethod = "GET", value = "Download Drug Sample File",
            notes = "This method will Download Drug File",
            produces = "application/json", nickname = "Download Drug Sample File",
            response = GenericAPIResponse.class, protocols = "https")
    @RequestMapping(value = "/drugSample", method = RequestMethod.GET)
    public ResponseEntity<?> drugSampleFileDownload() {
        logger.info("drugSampleFileDownload method initialized successfully...");
        GenericAPIResponse response = new GenericAPIResponse();
        try {
            URL resource = getClass().getClassLoader().getResource("sample_files/drug.xlsx");
            if (resource == null) {
                logger.error("Drug sample file not found...");
                return this.sampleFileNotFound();
            }

            File file = new File(resource.getPath());
            if (!file.exists()) {
                logger.error("Drug sample file not found...");
                return this.sampleFileNotFound();
            }
            boolean readOnly = file.setReadOnly();

            this.openFile(file.getCanonicalPath());
            response.setResponseMessage(messageBundle.getString("sample.file.download.success"));
            response.setResponseCode(InvoiceMessageEnum.SUCCESS.getValue());
            response.setResponseStatus(InvoiceMessageEnum.SUCCESS.getValue());
            logger.info("Drug sample file downloaded successfully...");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Drug sample file download Failed...", ex.fillInStackTrace());
            return this.sampleFileDownloadFailed(ex);
        }
    }

    private void openFile(@NotNull String canonicalPath) throws IOException, InterruptedException {
        String command = "rundll32 url.dll,FileProtocolHandler " + canonicalPath;
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
        System.out.println(canonicalPath);
    }

    private ResponseEntity<GenericAPIResponse> sampleFileNotFound() {
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
        response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
        response.setResponseMessage(messageBundle.getString("sample.file.download.no.record"));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<GenericAPIResponse> sampleFileDownloadFailed(Exception ex) {
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseStatus(InvoiceMessageEnum.ERROR.getValue());
        response.setResponseCode(InvoiceMessageEnum.EXCEPTION.getValue());
        response.setErrorMessageData(ex.getMessage());
        response.setResponseMessage(messageBundle.getString("sample.file.download.failed"));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
