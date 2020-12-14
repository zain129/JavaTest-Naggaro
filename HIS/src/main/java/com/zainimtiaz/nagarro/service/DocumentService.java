package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.Document;
import com.zainimtiaz.nagarro.model.Organization;
import com.zainimtiaz.nagarro.model.Patient;
import com.zainimtiaz.nagarro.repository.DocumentRepository;
import com.zainimtiaz.nagarro.repository.PatientRepository;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.DocumentWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by jamal on 9/3/2018.
 */
@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationService organizationService;

    @Transactional
    public String saveDocument(DocumentWrapper documentWrapper) {
        try {

            Document document = null;
            Patient patient = this.patientRepository.findOne(Long.valueOf(documentWrapper.getPatientId()));
            if (patient != null) {
                document = new Document(documentWrapper);
                document.setPatient(patient);
            }
            Organization dbOrganization=organizationService.getAllOrgizationData();
            String systemDateFormat=dbOrganization.getDateFormat();
            String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
            Date dte=new Date();
            String utcDate = HISCoreUtil.convertDateToTimeZone(dte,systemDateFormat,Zone);
            String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
            String readDate=HISCoreUtil.convertDateToTimeZone(dte,"YYYY-MM-dd hh:mm:ss",Zone);
            Date scheduledDate=HISCoreUtil.convertStringDateObject(readDate);
            document.setUpdatedOn(scheduledDate);
            this.documentRepository.save(document);

            String url = null;
            if (documentWrapper.getImage() != null) {
                url = userService.saveImage(documentWrapper.getImage(),
                        HISConstants.S3_USER_DOCUMENT_DIRECTORY_PATH,
                        documentWrapper.getPatientId()
                                + "_"
                                + document.getId()
                                + "_"
                                + HISConstants.S3_USER_DOCUMENT_THUMBNAIL_GRAPHIC_NAME,
                        documentWrapper.getPatientId()
                                + "_"
                                + document.getId()
                                + "_"
                                + HISConstants.S3_USER_DOCUMENT_GRAPHIC_NAME,
                        "/"
                                + HISConstants.S3_USER_DOCUMENT_DIRECTORY_PATH
                                + documentWrapper.getPatientId()
                                + "_"
                                + document.getId()
                                + "_"
                                + HISConstants.S3_USER_DOCUMENT_THUMBNAIL_GRAPHIC_NAME);


                if (HISCoreUtil.isValidObject(url)) {
                    document.setUrl(url);
                    this.documentRepository.save(document);
                    url = null;
                }

          }
            return "";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Transactional
    public String updateDocument(DocumentWrapper documentWrapper) {
        Document document = this.documentRepository.findOne(documentWrapper.getId());
        try {
            if (document != null) {
                new Document(document, documentWrapper);
                Patient patient = this.patientRepository.findOne(Long.valueOf(documentWrapper.getPatientId()));
                if (patient != null) {
                    document.setPatient(patient);
                } else {
                    return "patient not found";
                }

                this.documentRepository.save(document);

                String url = null;
                if (documentWrapper.getImage() != null) {
                    url = userService.saveImage(documentWrapper.getImage(),
                            HISConstants.S3_USER_DOCUMENT_DIRECTORY_PATH,
                            documentWrapper.getPatientId()
                                    + "_"
                                    + document.getId()
                                    + "_"
                                    + HISConstants.S3_USER_DOCUMENT_THUMBNAIL_GRAPHIC_NAME,
                            documentWrapper.getPatientId()
                                    + "_"
                                    + document.getId()
                                    + "_"
                                    + HISConstants.S3_USER_DOCUMENT_GRAPHIC_NAME,
                            "/"
                                    + HISConstants.S3_USER_DOCUMENT_DIRECTORY_PATH
                                    + documentWrapper.getPatientId()
                                    + "_"
                                    + document.getId()
                                    + "_"
                                    + HISConstants.S3_USER_DOCUMENT_THUMBNAIL_GRAPHIC_NAME);


                    if (HISCoreUtil.isValidObject(url)) {
                        document.setUrl(url);
                        this.documentRepository.save(document);
                        url = null;
                    }
                }
                return "";
            }
            return "Document not found";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Transactional
    public boolean deleteDocument(long documentId) {
        Document document = this.documentRepository.findOne(documentId);
        if (document != null) {
            this.documentRepository.delete(document);
            return true;
        }
        return false;
    }

    public DocumentWrapper getDocument(long documentId) {
        return this.documentRepository.findDocumentById(documentId);
    }

    public List<DocumentWrapper> getPaginatedDocuments(Pageable pageable,Long patientId) {
        return this.documentRepository.getPaginatedDocuments(pageable,patientId);
    }

    public int countPaginatedDocuments() {
        return this.documentRepository.findAll().size();
    }

    public boolean isNameDocumentAvailableByPatientId(String nameDocument,Long patientId) {
        return this.documentRepository.isNameExists(nameDocument,patientId);
    }

    public boolean isNameDocumentAvailableAgainstDocumentIdAndPatientId(String nameDocument, Long documentId, Long patientId) {
        return this.documentRepository.isNameExistsAgainstId(nameDocument, documentId,patientId);
    }
}
