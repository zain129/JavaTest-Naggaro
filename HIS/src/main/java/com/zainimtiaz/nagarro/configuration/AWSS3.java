package com.zainimtiaz.nagarro.configuration;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.zainimtiaz.nagarro.enums.S3ContentTypes;
import com.zainimtiaz.nagarro.model.S3Bucket;
import com.zainimtiaz.nagarro.model.User;
import com.zainimtiaz.nagarro.service.S3BucketService;
import com.zainimtiaz.nagarro.service.UserService;
import com.zainimtiaz.nagarro.utill.GraphicsUtil;
import com.zainimtiaz.nagarro.utill.HISConstants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/*
 * @author    : irfan nasim
 * @Date      : 18-Jul-17
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
 * @Project   : adminportal
 * @Package   : com.sd.ap.configuration
 * @FileName  : AWSS3
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Component
public class AWSS3 {

    public static final Logger logger = LoggerFactory.getLogger(AWSS3.class);
    S3Client s3Client;

    @Autowired
    UserService userService;
    @Autowired
    S3BucketService s3BucketService;

    /**
     * Retrieves a single object from S3 with the specified Key name
     *
     * @param s3Key
     * @return the object as an InputStream if one exists. Otherwise null.
     */
    public InputStream getObject(String s3Key) {
        User user = userService.findById(1l);
        S3Bucket s3Bucket = s3BucketService.findActiveBucket();

        InputStream objectData = null;

        try {
            s3Client = new S3Client();
            S3Object object = s3Client.client.getObject(s3Bucket.getName(), s3Key);
            objectData = object.getObjectContent();
        } catch (AmazonServiceException ase) {
            logger.error("Error response from AWS while retreiving object", ase);
        } catch (AmazonClientException ace) {
            logger.error("Error response from AWS while retreiving object", ace);
        } catch (Exception ex) {
            logger.error("Unknown Error whilst getting object from S3", ex);
        }
        return objectData;
    }

    public BufferedImage getS3Object(String s3Key) {

        BufferedImage bufferedImage = null;
        S3Bucket s3Bucket = s3BucketService.findActiveBucket();
        s3Client = new S3Client();

        try {
            S3Object object = s3Client.client.getObject(s3Bucket.getName(), s3Key);
            bufferedImage = ImageIO.read(object.getObjectContent());
            object.getObjectContent().close();
        } catch (AmazonServiceException ase) {
            logger.error("Error response from AWS while retreiving object", ase);
        } catch (AmazonClientException ace) {
            logger.error("Error response from AWS while retreiving object", ace);
        } catch (Exception ex) {
            logger.error("Unknown Error whilst getting object from S3", ex);
        }
        return bufferedImage;
    }

    /**
     * listS3Objects will return all the objects listed in a bucket that have
     * the specified prefix.
     *
     * @param prefix
     * @return ArrayList<KeyVersion>
     */
    public ArrayList<DeleteObjectsRequest.KeyVersion> listObjects(String prefix) {

        ObjectListing objectListing;
        List<DeleteObjectsRequest.KeyVersion> justKeys = new ArrayList<>();
        S3Bucket s3Bucket = s3BucketService.findActiveBucket();
        s3Client = new S3Client();

        try {
            ListObjectsRequest listObjectRequest =
                    new ListObjectsRequest().withBucketName(s3Bucket.getName())
                            .withPrefix(prefix);

            objectListing = s3Client.client.listObjects(listObjectRequest);

            for (S3ObjectSummary s3ObjectSummary : objectListing.getObjectSummaries()) {
                justKeys.add(new DeleteObjectsRequest.KeyVersion(s3ObjectSummary.getKey()));
            }

        } catch (AmazonServiceException ase) {
            logger.error("Error response from AWS while listing object", ase);
        } catch (AmazonClientException ace) {
            logger.error("Error response from AWS while retreiving object", ace);
        } catch (Exception ex) {
            logger.error("Unknown Error whilst listing object from S3", ex);
        }
        return (ArrayList) justKeys;
    }

    public Map<String, BufferedImage> listS3Objects(String prefix) {

        ObjectListing objectListing;
        Map<String, BufferedImage> imagesMap = new LinkedHashMap<>();
        S3Bucket s3Bucket = s3BucketService.findActiveBucket();
        s3Client = new S3Client();

        try {
            ListObjectsRequest listObjectRequest =
                    new ListObjectsRequest().withBucketName(s3Bucket.getName())
                            .withPrefix(prefix);

            objectListing = s3Client.client.listObjects(listObjectRequest);

            for (S3ObjectSummary s3ObjectSummary : objectListing.getObjectSummaries()) {
                S3Object object = s3Client.client.getObject(s3Bucket.getName(), s3ObjectSummary.getKey());
                String[] key = s3ObjectSummary.getKey().split("/");
                imagesMap.put(key[key.length - 1], ImageIO.read(object.getObjectContent()));
                object.getObjectContent().close();
            }
        } catch (AmazonServiceException ase) {
            logger.error("Error response from AWS while listing object", ase);
        } catch (AmazonClientException ace) {
            logger.error("Error response from AWS while retreiving object", ace);
        } catch (Exception ex) {
            logger.error("Unknown Error whilst listing object from S3", ex);
        }
        return imagesMap;
    }

    /**
     * Places a single item from an input stream to S3 at the specified
     * endpoint. The file will have private access. ie. anybody with the URL can
     * access the file.
     *
     * @param s3Key
     * @param inputStream
     * @param metadata
     * @param acl         (CannedAccessControlList.Private)
     */
    public PutObjectResult putObject(
            String s3Key,
            InputStream inputStream,
            ObjectMetadata metadata,
            CannedAccessControlList acl) throws Exception {

        PutObjectResult result = null;
        S3Bucket s3Bucket = s3BucketService.findActiveBucket();
        s3Client = new S3Client();

        /*
         * Create an access control list ACL with canned permissions for the
         * uploaded file and put the object in S3
         */
        try {
            result = s3Client.client.putObject(new PutObjectRequest(
                    s3Bucket.getName(),
                    s3Key,
                    inputStream,
                    metadata).withCannedAcl(acl));

        } catch (AmazonServiceException ase) {
            logger.error("Error response from AWS while putting object", ase);
            throw ase;
        } catch (AmazonClientException ace) {
            logger.error("Error response from AWS while listing object", ace);
            throw ace;
        } catch (Exception e) {
            logger.error("Unknown Error whilst putting object to S3", e);
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.error("Unknown Error whilst putting object to S3", ex);
                }
            }
        }
        return result;
    }

    /**
     * Handles deleting single Objects from Non-Versioned Buckets.
     *
     * @param keyName
     */
    public void deleteObject(String keyName) throws Exception {

        try {
            S3Bucket s3Bucket = s3BucketService.findActiveBucket();
            s3Client = new S3Client();

            s3Client.client.deleteObject(new DeleteObjectRequest(s3Bucket.getName(), keyName));
        } catch (AmazonServiceException ase) {
            logger.error("Error response from AWS while deleting object", ase);
            throw ase;
        } catch (AmazonClientException ace) {
            logger.error("Error response from AWS while deleting object", ace);
            throw ace;
        }
    }

    /**
     * Handles deleting multiple objects from Non-Versioned Bucket.
     *
     * @param justKeys
     */
    public DeleteObjectsResult deleteObjects(
            ArrayList<DeleteObjectsRequest.KeyVersion> justKeys) {

        DeleteObjectsResult result = null;
        S3Bucket s3Bucket = s3BucketService.findActiveBucket();
        s3Client = new S3Client();

        try {
            /*
             * Multi-object delete by specifying only keys (no version ID)
             */
            DeleteObjectsRequest multiObjectDeleteRequest =
                    new DeleteObjectsRequest(s3Bucket.getName()).withQuiet(false);
            multiObjectDeleteRequest.setKeys(justKeys);

            if (justKeys.size() > 0) {

                try {
                    result = s3Client.client.deleteObjects(multiObjectDeleteRequest);
                } catch (MultiObjectDeleteException mode) {
            //        System.out.println(mode);
                }

            }

        } catch (AmazonServiceException ase) {
            logger.error("Error response from AWS while deleting objects", ase);
        } catch (AmazonClientException ace) {
            logger.error("Error response from AWS while deleting objects", ace);
        }
        return result;
    }

    /**
     * S3 requires a meta data object to save items. At a minimum it needs the
     * content length of the stream that will be saved. Additionally a content
     * type can be set. The content type can be determined from the extension
     * (eg. pdf, or jpg). However, it can also be specified using the Constants
     * defined.
     *
     * @param inputStream
     * @param contentType type specified from IConsultConstants
     * @return
     */
    public ObjectMetadata setMetadata(
            InputStream inputStream, S3ContentTypes contentType) {

        byte[] contentBytes;
        Long contentLength = null;

        // Get the stream bytes
        try {
            contentBytes = IOUtils.toByteArray(inputStream);
            contentLength = Long.valueOf(contentBytes.length);
        } catch (IOException e) {
            logger.error("Error while setting S3 metadata", e);
        }

        /*
         * Save the byte length to a ObjectMetadata
         */
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);

        String mime = null;
        switch (contentType) {
            case PDF:
                mime = "application/pdf";
                break;
            case JPEG:
                mime = "image/jpeg";
                break;
            case JPG:
                mime = "image/jpeg";
                break;
            case PNG:
                mime = "image/png";
                break;
            case GIF:
                mime = "image/gif";
                break;
            case BMP:
                mime = "image/bmp";
                break;
            case TIFF:
                mime = "image/tiff";
                break;
            case PLAIN:
                mime = "text/plain";
                break;
            case RTF:
                mime = "text/rtf";
                break;
            case MSWORD:
                mime = "application/msword";
                break;
            case ZIP:
                mime = "application/zip";
                break;
        }
        if (mime != null) {
            metadata.setContentType(mime);

        }
        return metadata;
    }

    /**
     * Performs image resizing before placing the image on S3 in the specified
     * location.
     *
     * @param bufferedImage
     * @param s3Key
     * @param height
     * @param width
     * @param contentType
     * @param acl
     * @return PutObjectResult
     */
    public PutObjectResult imageToS3(
            BufferedImage bufferedImage,
            String s3Key,
            int height,
            int width,
            S3ContentTypes contentType,
            CannedAccessControlList acl) throws Exception {

        PutObjectResult result = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            if (bufferedImage != null) {

                // Resize the image
                BufferedImage resizeImage =
                        new GraphicsUtil().resize(bufferedImage, height, width);

                // Supported content types for images
                String format = null;
                switch (contentType) {
                    case JPEG:
                        format = "jpg";
                        break;
                    case JPG:
                        format = "jpeg";
                        break;
                    case PNG:
                        format = "png";
                        break;
                    case GIF:
                        format = "gif";
                        break;
                    case BMP:
                        format = "bmp";
                        break;
                }

                // Write the buffered
                ImageIO.write(resizeImage, format, out);

                result = putObject(
                        s3Key,
                        new ByteArrayInputStream(out.toByteArray()),
                        setMetadata(
                                new ByteArrayInputStream(out.toByteArray()),
                                contentType),
                        acl);

            } else {
                // Image is null
                logger.error("BufferedImage cannot be null", bufferedImage);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                logger.error("Error closing the streams", ex);
            }
        }
        return result;
    }

    /**
     * Placing the image on S3 in the specifiedlocation.
     *
     * @param bufferedImage
     * @param s3Key
     * @param contentType
     * @param acl
     * @return PutObjectResult
     */
    public PutObjectResult imageToS3(
            BufferedImage bufferedImage,
            String s3Key,
            S3ContentTypes contentType,
            CannedAccessControlList acl) throws Exception {

        PutObjectResult result = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            if (bufferedImage != null) {

                // Supported content types for images
                String format = null;
                switch (contentType) {
                    case JPEG:
                        format = "jpg";
                        break;
                    case JPG:
                        format = "jpeg";
                        break;
                    case PNG:
                        format = "png";
                        break;
                    case GIF:
                        format = "gif";
                        break;
                    case BMP:
                        format = "bmp";
                        break;
                }

                // Write the buffered
                ImageIO.write(bufferedImage, format, out);

                result = putObject(
                        s3Key,
                        new ByteArrayInputStream(out.toByteArray()),
                        setMetadata(
                                new ByteArrayInputStream(out.toByteArray()),
                                contentType),
                        acl);

            } else {
                // Image is null
                logger.error("BufferedImage cannot be null", bufferedImage);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                logger.error("Error closing the streams", ex);
            }
        }
        return result;
    }

    /**
     * Create an S3 client to perform actions on AWS S3 objects.
     *
     * @author Tarka L'Herpiniere <info@tarka.tv>
     * @since Mar 10, 2013
     */
    protected class S3Client {

        private AmazonS3 client = null;

        public S3Client() {
            try {

                S3Bucket s3Bucket = s3BucketService.findActiveBucket();
                AWSCredentials credentials = new BasicAWSCredentials(s3Bucket.getAccessKey(), s3Bucket.getSecretKey());
                client = new AmazonS3Client(credentials);

            } catch (Throwable t) {
                System.err.println("Error creating AmazonDynamoDBClient: " + t);
            }
        }
    }

    public boolean  getFileFromS3Bucket(String fileName) throws IOException {

        S3Bucket s3Bucket = s3BucketService.findActiveBucket();
        boolean fileCreated=false;
        s3Client = new S3Client();
        String relPath="PatientOrder";
        String nameOfBucket=s3Bucket.getName() + "/"+ HISConstants.S3_USER_ORDER_DIRECTORY_PATH;
        s3Client = new S3Client();
        if(s3Client.client.doesBucketExist("hisdev")){

            String path="users/patient/history/order";
            S3Object object = s3Client.client.getObject("hisdev/"+path,fileName);
       //     System.out.println("Etag:" + object.getKey() + "-->" + object.getBucketName());
            if (object == null) {

            }else{
                try (S3ObjectInputStream inputStream = object.getObjectContent()) {
                    try {
                        this.saveFile(fileName, relPath, inputStream);
                        fileCreated=true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        /* File file=new File("PatientOrder/"+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] read_buf = new byte[1024];
        int read_len = 0;
        while ((read_len = inputStream.read(read_buf)) > 0) {
            fos.write(read_buf, 0, read_len);
        }
        inputStream.close();
        fos.close();
        fileCreated=true;
            if(fileCreated){
                if(file.exists()){
                    System.out.println("File existed");
                    //   file.delete();
                }else{
                    System.out.println("File not found!");
                }
            }*/
            }
           /* List<String> objlist=this.getObjectslistFromFolder(s3Bucket.getName(),path);

            if(objlist.size()>0){

            }*/
       //     boolean fileExist=this.isExist(nameOfBucket,fileName);


        }else{

        }


        return  fileCreated;
    }


   /* public boolean isFilePublic(String rootFolderName, String fileName) {
        AccessControlList acl = s3Client.client.getObjectAcl(rootFolderName, fileName);
        for (Iterator<Grant> iterator = acl.getGrants().iterator(); iterator.hasNext(); ) {
            Grant grant = iterator.next();
            if (grant.getPermission().equals(Permission.Read) && grant.getGrantee().getIdentifier().equals("http://acs.amazonaws.com/groups/global/AllUsers"))
                return true;
        }
        return false;
    }*/


    public void  saveFile(String fileName, String path, InputStream objectData) throws Exception {
   //     DataOutputStream dos = null;
   //     OutputStream out = null;
        try {
            File newDirectory = new File(path);
            if (!newDirectory.exists()) {
                newDirectory.mkdirs();
            }

            File uploadedFile = new File(path, fileName);
            copyInputStreamToFile(objectData,uploadedFile);
            /*out = new FileOutputStream(uploadedFile);
            byte[] fileAsBytes = new byte[objectData.available()];
            objectData.read(fileAsBytes);

            dos = new DataOutputStream(out);
            dos.write(fileAsBytes);*/
        } /*catch (IOException io) {
            io.printStackTrace();
        }*/ catch (Exception e) {
            e.printStackTrace();
        } finally {
           /* try {
                if (out != null) {
                    out.close();
                }
                if (dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

  /*  public void downloadFile(String bucketName,String keyName,String fileName) {

        try {

            System.out.println("Downloading an object");
            S3Object s3object = s3Client.client.getObject(new GetObjectRequest(bucketName, keyName),fileName));
            System.out.println("Content-Type: "  + s3object.getObjectMetadata().getContentType());
         //   Utility.displayText(s3object.getObjectContent());
            logger.info("===================== Import File - Done! =====================");

        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        } catch (IOException ioe) {
            logger.info("IOE Error Message: " + ioe.getMessage());
        }
    }*/



    /*public ResponseEntity<Resource> downloadFile(String filename){

        String bucketPath = "s3://" + bucket + "/";
        Resource s3Resource = resourceLoader.getResource(bucketPath + filename);

        String s3FileName = filename.substring(filename.lastIndexOf("/"));
        s3FileName = s3FileName.replace("/", "");

        logger.info("Downloading File: {} from S3", s3FileName);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+s3FileName+"\"")
                .body(s3Resource);

    }*/

    /*boolean isExist(String bucketName,String key)
    {
        ObjectListing objects = s3Client.client.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix(key));

        for (S3ObjectSummary objectSummary : objects.getObjectSummaries())
        {
            if (objectSummary.getKey().equals(key))
            {
                return true;
            }

        }
        return false;
    }*/


   /* public List<String> getObjectslistFromFolder(String bucketName, String folderKey) {

        ListObjectsRequest listObjectsRequest =
                new ListObjectsRequest()
                        .withBucketName(bucketName)
                        .withPrefix(folderKey + "/");

        List<String> keys = new ArrayList<>();

        ObjectListing objects = s3Client.client.listObjects(listObjectsRequest);
        for (;;) {
            List<S3ObjectSummary> summaries = objects.getObjectSummaries();
            if (summaries.size() < 1) {
                break;
            }
            summaries.forEach(s -> keys.add(s.getKey()));
            objects = s3Client.client.listNextBatchOfObjects(objects);
        }

        return keys;
    }*/



 /*   public static List<String> getS3ImmediatePaths(AmazonS3 s3Client, String bucketName, String keyPrefix) {
        List<String> paths = new ArrayList<String>();
        String delimiter = "/";
        if (keyPrefix != null && !keyPrefix.isEmpty() && !keyPrefix.endsWith(delimiter)) {
            keyPrefix += delimiter;
        }

        ListObjectsRequest listObjectRequest = new ListObjectsRequest().withBucketName(bucketName)
                .withPrefix(keyPrefix).withDelimiter(delimiter);

        ObjectListing objectListing;
        do {
            objectListing = s3Client.listObjects(listObjectRequest);
            paths.addAll(objectListing.getCommonPrefixes());
            listObjectRequest.setMarker(objectListing.getNextMarker());
        } while (objectListing.isTruncated());
        return paths;
    }*/


    private void copyInputStreamToFile( InputStream in, File file ) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteObjectSingle(String keyName) throws Exception {

        try {
            S3Bucket s3Bucket = s3BucketService.findActiveBucket();
            s3Client = new S3Client();

            s3Client.client.deleteObject(new DeleteObjectRequest(s3Bucket.getName(), keyName));
        } catch (AmazonServiceException ase) {
            logger.error("Error response from AWS while deleting object", ase);
            throw ase;
        } catch (AmazonClientException ace) {
            logger.error("Error response from AWS while deleting object", ace);
            throw ace;
        }
    }
}
