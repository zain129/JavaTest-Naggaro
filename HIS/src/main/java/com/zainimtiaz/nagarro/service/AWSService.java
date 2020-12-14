package com.zainimtiaz.nagarro.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.zainimtiaz.nagarro.configuration.AWSS3;
import com.zainimtiaz.nagarro.configuration.S3KeyGen;
import com.zainimtiaz.nagarro.enums.S3ContentTypes;
import com.zainimtiaz.nagarro.model.S3Bucket;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.utill.HISConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

/*
 * @author    : irfan Nasim
 * @Date      : 26-Jun-18
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
 * @Package   : com.sd.his.service
 * @FileName  : AWSService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service("awsService")
public class AWSService {
    private static int IMG_WIDTH = 100;
    private static int IMG_HEIGHT = 100;
    public static final Logger logger = LoggerFactory.getLogger(AWSService.class);
    @Autowired
    S3KeyGen s3KeyGen;
    @Autowired
    AWSS3 awss3;

    @Autowired
    S3BucketService s3BucketService;

    private AmazonS3 s3client;

    public boolean saveProfileImage(InputStream inputStream, Long id) {

        boolean returnValue = false;

        BufferedImage bufferedImage;

        try {
            // Convert the event input stream into a buffered image
            bufferedImage = ImageIO.read(inputStream);

            // Generate an s3Key
            String[] s3Key = s3KeyGen.profileGraphic(id);

            // Save the image to S3
            awss3.imageToS3(
                    bufferedImage,
                    s3Key[1],
                    S3ContentTypes.PNG,
                    CannedAccessControlList.PublicRead);
            returnValue = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public boolean uploadImage(InputStream inputStream, Long id) {
        boolean returnValue = false;
        BufferedImage bufferedImage;

        try {
            // Convert the event input stream into a buffered image
            bufferedImage = ImageIO.read(inputStream);
            int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
            IMG_WIDTH = bufferedImage.getWidth() / 5;
            IMG_HEIGHT = bufferedImage.getHeight() / 5;

            BufferedImage resizeImageJpg = resizeImage(bufferedImage, type);
            String[] s3ThumbnailKey = s3KeyGen.profileThumbnailGraphic(id);
            String[] s3Key = s3KeyGen.profileGraphic(id);
             /*String[] s3ThumbnailKey = s3KeyGen.profileGraphic(id);
            String[] s3Key = s3KeyGen.profileGraphic(id)*/;
            // Save the full image to S3
            awss3.imageToS3(
                    bufferedImage,
                    s3Key[1],
                    S3ContentTypes.PNG,
                    CannedAccessControlList.PublicRead);

            // Save the thumbnails image to S3
            awss3.imageToS3(
                    resizeImageJpg,
                    s3ThumbnailKey[1],
                    S3ContentTypes.PNG,
                    CannedAccessControlList.PublicRead);
            returnValue = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public boolean uploadImageByUserId(InputStream inputStream,
                                       String directoryPath,
                                       String thumbnailGraphicName,
                                       String graphicName) throws Exception {
        /**
         * Convert the event input stream into a buffered image
         * */
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
        IMG_WIDTH = bufferedImage.getWidth() / 5;
        IMG_HEIGHT = bufferedImage.getHeight() / 5;

        BufferedImage resizeImageJpg = resizeImage(bufferedImage, type);

        String[] s3ThumbnailKey = s3KeyGen.thumbnailGraphic(
                directoryPath + thumbnailGraphicName);

        String[] s3Key = s3KeyGen.graphic(
                directoryPath + graphicName);

        // Save the full image to S3
        awss3.imageToS3(
                bufferedImage,
                s3Key[1],
                S3ContentTypes.PNG,
                CannedAccessControlList.PublicRead);

        // Save the thumbnails image to S3

        awss3.imageToS3(
                resizeImageJpg,
                s3ThumbnailKey[1],
                S3ContentTypes.PNG,
                CannedAccessControlList.PublicRead);

        return true;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

    public String getProfileImageUrl(Long id) {
        return s3KeyGen.getUserProfileGraphicPublicUserId(id, false);
    }

    public String getProfileThumbnailImageUrl(Long id) {
        return s3KeyGen.getUserProfileThumbnailGraphicPublicUserId(id, false);
    }

    public String getThumbnailImageUrl(long id,String fullPathAndThumbnailGraphicName) {
        return s3KeyGen.getThumbnailGraphicPublicUserId(id,fullPathAndThumbnailGraphicName, false);
    }



    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
       /* FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();*/
        return convFile;
    }

    public String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }



    public String uploadFile(MultipartFile multipartFile,long patientId) {

        String fileUrl = "";
        S3Bucket s3Bucket = s3BucketService.findActiveBucket();
        try {
         //   File file = HISCoreUtil.multipartToFile(multipartFile);//convertMultiPartToFile(multipartFile);
           // String fileName =generateFileName(multipartFile);
            String fileName=multipartFile.getOriginalFilename();
            fileUrl = s3Bucket.getAccessProtocol()+s3Bucket.getPublicBaseURL() + "/" + s3Bucket.getName() + "/"+ HISConstants.S3_USER_ORDER_DIRECTORY_PATH+patientId+"_"+fileName;
            String fileNameIrl=uploadFileTos3bucket(multipartFile, true,patientId,fileUrl);
            fileUrl=s3Bucket.getAccessProtocol()+s3Bucket.getPublicBaseURL() + "/" + s3Bucket.getName() + "/"+ HISConstants.S3_USER_ORDER_DIRECTORY_PATH+fileNameIrl;
            if(fileUrl!=null || fileUrl.equals("")){

                fileUrl=fileUrl;
            }else{

                fileUrl="";
            }
            /* if(file.exists()){
            file.delete();
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }



   /* private void uploadFileTos3bucket(String fileName, File file,String url) {

        S3Bucket s3Bucket = s3BucketService.findActiveBucket();

        try{s3client.putObject(new PutObjectRequest(s3Bucket.getName(), url+fileName.toString(), file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        }catch (AmazonServiceException ase) {
        logger.error("Error response from AWS while putting object", ase);
        throw ase;
        } catch (AmazonClientException ace) {
        logger.error("Error response from AWS while listing object", ace);
        throw ace;
        } catch (Exception e) {
        logger.error("Unknown Error whilst putting object to S3", e);
        throw e;
        }
}*/

    private String uploadFileTos3bucket(MultipartFile multipartFile, boolean enablePublicReadAccess,long Id,String fileurl) {


        S3Bucket s3Bucket = s3BucketService.findActiveBucket();
        String fileName = Id+"_"+ HISCoreUtil.convertDateToStringUpload(new Date())+"_"+multipartFile.getOriginalFilename();
        String fileUrl = "";
     //   String relativePath = "PatientOrder";
     //   File file = new File(relativePath+fileName);
        File file = new File(fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
        //    fos.write(multipartFile.getBytes());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());
            fileUrl = s3Bucket.getPublicBaseURL() + "/" + s3Bucket.getName() + "/"+ HISConstants.S3_USER_ORDER_DIRECTORY_PATH+fileName;
            String bucketName= HISConstants.S3_USER_ORDER_DIRECTORY_PATH+fileName;

            try {
                PutObjectResult result = this.awss3.putObject(bucketName, fis, metadata, CannedAccessControlList.PublicRead);
             //   System.out.println("Etag:" + result.getETag() + "-->" + result);
                if(result==null){
                 //   fileName="";
                }else{

                    fileName="";

                }
           //     File theDir = new File(relativePath);
          //      boolean flagResult=true;
                /*if (!theDir.exists()) {
                    System.out.println("creating directory: " + theDir.getName());
                     flagResult = false;

                    try {
                        theDir.mkdir();
                        flagResult = true;
                    } catch (SecurityException se) {
                        //handle it
                    }
                    if (flagResult) {
                        System.out.println("DIR created");
                    }
                }else{
                    if(flagResult){
                    if(file.exists()){
                        System.out.println("File existed");
                     //   file.delete();
                    }else{
                        System.out.println("File not found!");
                    }
                } }*/
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException | AmazonServiceException ex) {
            logger.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
        }finally {
            if(file.exists()){
           //     System.out.println("File existed");
                file.delete();
            }else{
          //      System.out.println("File not found!");
            }
        }
        return fileName;
    }

  /*  public File getFile(String fileName) throws Exception {
        if (StringUtils.isEmpty(fileName)) {
            throw new Exception("file name can not be empty");
        }
       // S3Object s3Object =
        String bucketName=S3_USER_ORDER_DIRECTORY_PATH+fileName;
        S3Object obj = this.awss3.getObject;
        if (s3Object == null) {
            throw new Exception("Object not found");
        }
        File file = new File("you file path");
        Files.copy(s3Object.getObjectContent(), file.toPath());
        inputStream.close();
        return file;
    }*/

    public boolean uploadImageByUserIdDeleteBefore(InputStream inputStream,
                                       String directoryPath,
                                       String thumbnailGraphicName,
                                       String graphicName,String oldFileUrl) throws Exception {
        /**
         * Convert the event input stream into a buffered image
         * */
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
        IMG_WIDTH = bufferedImage.getWidth() / 5;
        IMG_HEIGHT = bufferedImage.getHeight() / 5;

        BufferedImage resizeImageJpg = resizeImage(bufferedImage, type);

        String[] s3ThumbnailKey = s3KeyGen.thumbnailGraphic(
                directoryPath + thumbnailGraphicName);

        String[] s3Key = s3KeyGen.graphic(
                directoryPath + graphicName);

        // Save the full image to S3
     //   S3Bucket s3Bucket = s3BucketService.findActiveBucket();
     //   String keyName=S3_USER_ORGANIZATION_DIRECTORY_PATH+oldFileUrl;

        /*if(!oldFileUrl.equals("user-yellow.png")){
        this.awss3.deleteObjectSingle(keyName);
        }*/
        awss3.imageToS3(
                bufferedImage,
                s3Key[1],
                S3ContentTypes.PNG,
                CannedAccessControlList.PublicRead);

        // Save the thumbnails image to S3

        awss3.imageToS3(
                resizeImageJpg,
                s3ThumbnailKey[1],
                S3ContentTypes.PNG,
                CannedAccessControlList.PublicRead);

        return true;
    }




    public boolean uploadImageByUserIdDeleteBeforeProfile(InputStream inputStream,
                                                   String directoryPath,
                                                   String thumbnailGraphicName,
                                                   String graphicName,String oldFileUrl) throws Exception {
        /**
         * Convert the event input stream into a buffered image
         * */
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
        IMG_WIDTH = bufferedImage.getWidth() / 5;
        IMG_HEIGHT = bufferedImage.getHeight() / 5;

        BufferedImage resizeImageJpg = resizeImage(bufferedImage, type);

        String[] s3ThumbnailKey = s3KeyGen.thumbnailGraphic(
                directoryPath + thumbnailGraphicName);

        String[] s3Key = s3KeyGen.graphic(
                directoryPath + graphicName);

        // Save the full image to S3
        S3Bucket s3Bucket = s3BucketService.findActiveBucket();
        String keyName= HISConstants.S3_USER_PROFILE_NEW_DIRECTORY_PATH+oldFileUrl;

        if(!oldFileUrl.equals("user-yellow.png")){
            this.awss3.deleteObjectSingle(keyName);
        }
        awss3.imageToS3(
                bufferedImage,
                s3Key[1],
                S3ContentTypes.PNG,
                CannedAccessControlList.PublicRead);

        // Save the thumbnails image to S3

        awss3.imageToS3(
                resizeImageJpg,
                s3ThumbnailKey[1],
                S3ContentTypes.PNG,
                CannedAccessControlList.PublicRead);

        return true;
    }

}
