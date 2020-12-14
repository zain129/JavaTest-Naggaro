package com.zainimtiaz.nagarro.service;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.zainimtiaz.nagarro.configuration.AWSS3;
import com.sd.his.model.*;
import com.sd.his.repository.*;
import com.zainimtiaz.nagarro.model.*;
import com.zainimtiaz.nagarro.repository.PatientImageRepository;
import com.zainimtiaz.nagarro.repository.PatientOrderRepository;
import com.zainimtiaz.nagarro.utill.DateTimeUtil;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.Patient_OrderWrapper;
import com.zainimtiaz.nagarro.wrapper.request.Patient_OrderWrapper_Update;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zainimtiaz.nagarro.repository.PatientRepository;
import org.springframework.web.multipart.MultipartFile;



@Service
public class PatientOrderService {

    @Autowired
    private PatientOrderRepository patientOrderRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private UserService userService;
    @Autowired
    PatientImageRepository patientImageRepository;

    @Autowired
    AWSService awsService;
    @Autowired
    S3BucketService s3BucketService;

    @Autowired
    AWSS3 awss3;
    @Autowired
    private OrganizationService organizationService;
    @Transactional
    public String saveOrder(Patient_OrderWrapper orderWrapper) {

        Organization dbOrganization=organizationService.getAllOrgizationData();
        String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
        String systemDateFormat=dbOrganization.getDateFormat();
        String systemTimeFormat=dbOrganization.getTimeFormat();
        String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
        String hoursFormat = dbOrganization.getHoursFormat();
        String standardSystem=systemDateFormat+" "+systemTimeFormat;
        Date dteOrder=null;
        try {

            Patient_Order patientOrder = null;
            Patient patient = this.patientRepository.findOne(Long.valueOf(orderWrapper.getPatientId()));
            PatientImageSetup patientImageSetup=patientImageRepository.findOne(Long.valueOf(orderWrapper.getPatientImageId()));
            if (patient != null) {
                patientOrder= new Patient_Order(orderWrapper);
                patientOrder.setPatient(patient);
            }
            if(patientImageSetup!=null){
                patientOrder.setOrder(patientImageSetup);
            }

         //   this.patientOrderRepository.save(patientOrder);
            List<String> imageUrls=new ArrayList<>();
            String url = null;
            if (orderWrapper.getListOfFiles().length > 0 ) {

                    for (MultipartFile multipartFile : orderWrapper.getListOfFiles()) {

                        String fileName = multipartFile.getOriginalFilename();
                        String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
                      //  patientOrder.setType(tokens[1].toString());

                        byte[] bteObj = multipartFile.getBytes();
                        if(tokens[1].toString().equalsIgnoreCase("PNG") || tokens[1].toString().equalsIgnoreCase("JPG")   || tokens[1].toString().equalsIgnoreCase("JPEG") || tokens[1].toString().equalsIgnoreCase("GIF") || tokens[1].toString().equalsIgnoreCase("BMP") ) {
                            url = userService.saveImageByOrder(bteObj,
                                    HISConstants.S3_USER_ORDER_DIRECTORY_PATH,
                                    orderWrapper.getPatientId()
                                            + "_"
                                            + HISCoreUtil.convertDateToStringUpload(new Date())//patientImageSetup.getId()
                                            + "_"
                                            + HISConstants.S3_USER_ORDER_DIRECTORY_PATH,
                                    orderWrapper.getPatientId()
                                            + "_"
                                            + HISCoreUtil.convertDateToStringUpload(new Date())
                                            + "_"
                                            + HISConstants.S3_USER_ORDER_GRAPHIC_NAME,
                                    "/"
                                            + HISConstants.S3_USER_ORDER_DIRECTORY_PATH
                                            + orderWrapper.getPatientId()
                                            + "_"
                                            + HISCoreUtil.convertDateToStringUpload(new Date())
                                            + "_"
                                            + HISConstants.S3_USER_ORDER_THUMBNAIL_GRAPHIC_NAME);


                        }else{

                           url =    awsService.uploadFile(multipartFile,orderWrapper.getPatientId() );



                        }

                        imageUrls.add(url);
                    }
             //       System.out.println(url);
                if (!HISCoreUtil.isListEmpty(imageUrls)) {
                    patientOrder.setUrl(imageUrls);
                    this.patientOrderRepository.save(patientOrder);

                    url = null;
                }
                    return "";
                }else{
             //   dteDia= DateTimeUtil.getDateFromString(new Date(), "yyyy-MM-dd hh:mm:ss");
                String dZoneDate = HISCoreUtil.convertDateToTimeZone(new Date(), "yyyy-MM-dd hh:mm:ss", Zone);
                dteOrder= DateTimeUtil.getDateFromString(dZoneDate, "yyyy-MM-dd hh:mm:ss");
              //  if (systemDateFormat != null || !systemDateFormat.equals("")) {
                   // String sdDate=HISCoreUtil.convertDateToStringWithDateDisplay(dteOrder,systemDateFormat);
                    patientOrder.setDateOrder(dteOrder);
            //    }
                this.patientOrderRepository.save(patientOrder);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Transactional
    public String updateDocument(Patient_OrderWrapper_Update orderWrapper) {
        Patient_Order patientOrder  = this.patientOrderRepository.findOne(orderWrapper.getPatientId());
        Organization dbOrganization=organizationService.getAllOrgizationData();
        String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
     //   String systemDateFormat=dbOrganization.getDateFormat();
     //   String systemTimeFormat=dbOrganization.getTimeFormat();
     //   String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
    //    String hoursFormat = dbOrganization.getHoursFormat();
    //    String standardSystem=systemDateFormat+" "+systemTimeFormat;
        Date dteOrder=null;
        try {
            if (patientOrder != null) {
              //  new Patient_OrderWrapper_Update(patientOrder, orderWrapper);
                Patient patient = this.patientRepository.findOne(Long.valueOf(orderWrapper.getPatientId()));
                if (patient != null) {
                    patientOrder.setPatient(patient);
                } else {
                    return "patient not found";
                }
                patientOrder.setDescription(orderWrapper.getDescription());
                patientOrder.setStatus(orderWrapper.getStatus());
                patientOrder.setDoctorComment(orderWrapper.getDoctorComment());
                String dZoneDate = HISCoreUtil.convertDateToTimeZone(new Date(), "yyyy-MM-dd hh:mm:ss", Zone);
            //    dteOrder= DateTimeUtil.getDateFromString(dZoneDate, "yyyy-MM-dd hh:mm:ss");
            //    patientOrder.setDateOrder(dteOrder);

                this.patientOrderRepository.save(patientOrder);

            }
            return "Order  not found";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Transactional
    public boolean deleteDocument(long documentId) {
        Patient_Order patientOrder  = this.patientOrderRepository.findOne(documentId);
        if (patientOrder != null) {
            this.patientOrderRepository.delete(patientOrder);
            return true;
        }
        return false;
    }

    public Patient_OrderWrapper  getOrderById(long Id) {
       return this.patientOrderRepository.findOrderById(Id);
       // return null;
    }

    public List<Patient_OrderWrapper> getPaginatedOrder(Pageable pageable, Long patientId) {
        List<Patient_OrderWrapper> order = this.patientOrderRepository.getPaginatedOrder(pageable, patientId);
        Organization dbOrganization=organizationService.getAllOrgizationData();
        String Zone=dbOrganization.getZone().getName().replaceAll("\\s","");
        String systemDateFormat=dbOrganization.getDateFormat();
        String systemTimeFormat=dbOrganization.getTimeFormat();
        String currentTime= HISCoreUtil.getCurrentTimeByzone(Zone);
        String hoursFormat = dbOrganization.getHoursFormat();
        String standardSystem=systemDateFormat+" "+systemTimeFormat;
        Date dteOrder=null;
        boolean executeFlow = false;
        List<Patient_OrderWrapper> orderWrapperLst = new ArrayList<Patient_OrderWrapper>();
        for (int i = 0; i < order.size(); i++) {
            int size= order.get(i).getUrl().size();
            Patient patient = patientRepository.findOne(order.get(i).getPatientId());

            if(size==0) {
                Patient_OrderWrapper orderWrapper = new Patient_OrderWrapper();
                orderWrapper.setId(order.get(i).getId());
                orderWrapper.setDescription(order.get(i).getDescription());
                //  orderWrapper.setUrl(order.get(i).getUrl());
                orderWrapper.setDoctorComment(order.get(i).getDoctorComment());
                orderWrapper.setOrderObj(order.get(i).getOrderObj());
                orderWrapper.setStatus(order.get(i).getStatus());
                orderWrapper.setPatient(patient);
                //orderWrapper.setStrUrl(order.get(i).getUrl(j));
            }
            for (int j = 0; j < order.get(i).getUrl().size(); j++) {

                if (executeFlow == false) {
                    Patient_OrderWrapper orderWrapper = new Patient_OrderWrapper();
                    orderWrapper.setId(order.get(i).getId());
                    orderWrapper.setDescription(order.get(i).getDescription());
                    //  orderWrapper.setUrl(order.get(i).getUrl());
                    orderWrapper.setDoctorComment(order.get(i).getDoctorComment());
                    orderWrapper.setOrderObj(order.get(i).getOrderObj());
                    orderWrapper.setStatus(order.get(i).getStatus());
                    orderWrapper.setPatient(patient);
                 //   orderWrapper.setUrl(order.get(j).getUrl());
                    orderWrapper.setStrUrl(order.get(i).getUrl().get(j).toString());
                    dteOrder= HISCoreUtil.convertStringDateObject(order.get(i).getStrCreatedDate());
                    String dZoneDate = HISCoreUtil.convertDateToTimeZone(dteOrder, "yyyy-MM-dd hh:mm:ss", Zone);
                    dteOrder= HISCoreUtil.convertStringDateObject(dZoneDate);
                      if (systemDateFormat != null || !systemDateFormat.equals("")) {
                         String sdDate=HISCoreUtil.convertDateToStringWithDateDisplay(dteOrder,systemDateFormat);
                        orderWrapper.setStrCreatedDate(sdDate);
                          orderWrapperLst.add(orderWrapper);
                        }
                   /* wrapObj.setTestDate(obj.get(i).getDateTest());
                    wrapObj.setComments(obj.get(i).getComments());
                    wrapObj.setAppointment(obj.get(i).getAppointment());
                    wrapObj.setPatient(obj.get(i).getPatient());
                    wrapObj.setId(obj.get(i).getId());
                    wrapObj.setOrderStatus(obj.get(i).getStatus());*/
                }

          //      Patient_OrderWrapper orderWrapper = new Patient_OrderWrapper();


            }




        }
        return orderWrapperLst;
    }
    public int countPaginatedDocuments() {
        return this.patientOrderRepository.findAll().size();
    }

    public boolean isNameDocumentAvailableByPatientId(String nameDocument,Long patientId) {
        return this.patientOrderRepository.isNameExists(nameDocument,patientId);
    }

    public boolean isNameDocumentAvailableAgainstDocumentIdAndPatientId(String nameDocument, Long documentId, Long patientId) {
        return this.patientOrderRepository.isNameExistsAgainstId(nameDocument, documentId,patientId);
    }


    public Patient_OrderWrapper  getOrderImageById(long Id) {
        return this.patientOrderRepository.findOrderImageById(Id);
        // return null;
    }

    public Patient_OrderWrapper  getImagesOrderById(long Id) {
        Patient_OrderWrapper wrapperObj= this.patientOrderRepository.findOrderById(Id);
         return wrapperObj;
    }


    public String   getOrderImageById(long Id,String fileName) {
        Patient_OrderWrapper wrapPatient = this.patientOrderRepository.findOrderImageById(Id);
        String path="";
        if(wrapPatient!=null){
            if(wrapPatient.getUrl().size()>0){
                for(int i=0;i<wrapPatient.getUrl().size();i++){


                    String urlStr = wrapPatient.getUrl().get(i);

                    String fileNameDb = urlStr.substring(urlStr.lastIndexOf('/')+1, urlStr.length());

                    if(fileName.equals(fileNameDb)){
                        S3Bucket s3Bucket = s3BucketService.findActiveBucket();
                        String bucketName= s3Bucket.getName();
                        try {
                            boolean isPresent=this.awss3.getFileFromS3Bucket(fileName);
                        //    boolean isPresent=downloadUsingStream(urlStr,fileName);
                            if(isPresent){
                                try {
                                    File fileNameReturn=PatientOrderService.getFile(fileName);
                                    String url=System.getProperty("user.dir");
                                    String finalUrl=url+"/PatientOrder"+"/"+fileName;
                             //       File fileObj=fileNameReturn.getCanonicalFile();
                              //      String url=fileNameReturn.getParent();
                                    path = finalUrl;
                                   // path= fileNameReturn.getPath();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                }
            }

        }
              return path;
    }


    public static  File getFile(String fileName) throws Exception {
        if (StringUtils.isEmpty(fileName)) {
            throw new Exception("file name can not be empty");
        }

        File file = new File(fileName);
        if(file.exists()){
       //     System.out.println("File existed");
            //   file.delete();
        }else{
     //       System.out.println("File not found!");
        }

        return file;
    }


    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }


    private boolean   downloadUsingStream(String urlStr, String file) throws IOException{

        boolean retStatus=false;
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
            retStatus=true;
        }
        fis.close();
        bis.close();
        return retStatus;
    }


    public static void copyURLToFile(URL url, File file) {

        /*File file = new File("C:/Temp/file.zip");

        URLReader.copyURLToFile(url, file);*/
        try {
            InputStream input = url.openStream();
            if (file.exists()) {
                if (file.isDirectory())
                    throw new IOException("File '" + file + "' is a directory");

                if (!file.canWrite())
                    throw new IOException("File '" + file + "' cannot be written");
            } else {
                File parent = file.getParentFile();
                if ((parent != null) && (!parent.exists()) && (!parent.mkdirs())) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }

            FileOutputStream output = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }

            input.close();
            output.close();

       //     System.out.println("File '" + file + "' downloaded successfully!");
        }
        catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

}
