package com.zainimtiaz.nagarro.utill;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import com.zainimtiaz.nagarro.model.EmailConfiguration;
import com.zainimtiaz.nagarro.repository.EmailConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class AmazonSESUtil implements IEmailUtil {

    private AmazonSimpleEmailServiceClient client;
    private static AmazonSESUtil INSTANCE;

    private AmazonSESUtil() {
        initialize();
    }

    @Autowired
    EmailConfigurationRepository emailConfigurationRepository;

    private void initialize() {
        try {
            EmailConfiguration config = emailConfigurationRepository.findBySystemDefaultTrue();
            AWSCredentials credentials = new BasicAWSCredentials(config.getSesAccessKey(), config.getSesSecretKey());
            client = new AmazonSimpleEmailServiceClient(credentials);
        } catch (Exception t) {
            System.out.println("Error creating AmazonEmailClient: " + t);
        }

    }

    public static AmazonSESUtil getInstance(Boolean isNew) {
        if (INSTANCE == null || isNew) {
            INSTANCE = new AmazonSESUtil();
        }
        return INSTANCE;
    }

    @Override
    public Boolean sendEmail(
            String emailSender,
            String emailRecipient,
            String emailSubject,
            String emailHtmlBody) {

        try {
            SendEmailRequest request = new SendEmailRequest().withSource(emailSender);
            List<String> toAddresses = new ArrayList<>();
            toAddresses.add(emailRecipient);
            Destination dest = new Destination().withToAddresses(toAddresses);
            request.setDestination(dest);
            Content subjContent = new Content().withData(emailSubject);
            Message msg = new Message().withSubject(subjContent);
            Content htmlContent = new Content().withData(emailHtmlBody);
            Body body = new Body().withHtml(htmlContent);
            msg.setBody(body);
            request.setMessage(msg);
            client.sendEmail(request);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean sendTestEmail(
            String emailSender,
            String emailRecipient,
            String emailSubject,
            String emailHtmlBody, String accessKey, String secretKey) throws Exception, AmazonClientException {
        Boolean flag = false;
        AmazonSimpleEmailServiceClient client = createConnection(accessKey, secretKey);
        if (client == null) {
            flag = false;
        }
        try {
            SendEmailRequest request = new SendEmailRequest().withSource(emailSender);
            List<String> toAddresses = new ArrayList<>();
            toAddresses.add(emailRecipient);
            Destination dest = new Destination().withToAddresses(toAddresses);
            request.setDestination(dest);

            Content subjContent = new Content().withData(emailSubject);
            Message msg = new Message().withSubject(subjContent);

            /*
             * Include a body in both text and HTML formats
             */
            Content htmlContent = new Content().withData(emailHtmlBody);
            Body body = new Body().withHtml(htmlContent);
            msg.setBody(body);

            request.setMessage(msg);
            client.sendEmail(request);
            // Create an audit log of the event
            flag = true;
        } catch (Exception e) {
            System.out.println(e);

        }
        return flag;
    }

    private static AmazonSimpleEmailServiceClient createConnection(String accessKey, String secretKey) {
        AmazonSimpleEmailServiceClient client = null;
        try {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            client = new AmazonSimpleEmailServiceClient(credentials);
        } catch (Exception t) {
        }

        return client;
    }


}
