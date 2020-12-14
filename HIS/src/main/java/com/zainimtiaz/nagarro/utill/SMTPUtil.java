package com.zainimtiaz.nagarro.utill;

import com.zainimtiaz.nagarro.model.EmailConfiguration;
import com.zainimtiaz.nagarro.repository.EmailConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SMTPUtil implements IEmailUtil {

    @Autowired
    EmailConfigurationRepository emailConfigurationRepository;
    private Session session;
    private static SMTPUtil INSTANCE;

    private SMTPUtil() {

        initialize();
    }

    private void initialize() {
        try {
            EmailConfiguration config = emailConfigurationRepository.findBySystemDefaultTrue();
            Properties props = new Properties();
            props.put("mail.smtp.user", config.getSenderEmail().trim());
            props.put("mail.smtp.host", config.getSmtpHost().trim());
            props.put("mail.smtp.port", config.getSmtpPort().trim());
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.port", config.getSmtpPort().trim());
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(config.getSenderEmail().trim(), config.getSmptPassword());
                }
            };
            session = Session.getInstance(props, auth);
        } catch (Exception t) {
            System.out.println("Error creating AmazonEmailClient: " + t);
        }

    }

    public static synchronized SMTPUtil getInstance(Boolean isNew) {
        if (isNew || INSTANCE == null) {
            INSTANCE = new SMTPUtil();
        }
        return INSTANCE;
    }

   public static Boolean sendTestEmail(String senderEmail, String smtpServer, String smtpPort, String receiverEmail, String password) {

        try {
            Properties props = new Properties();
            props.put("mail.smtp.user", senderEmail);
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.port", smtpPort.trim());
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.port", smtpPort.trim());
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            //  Authenticator auth  =  new SMTPAuthenticator(senderEmail.trim(),password.trim())
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail.trim(), password);
                }
            };
            Session session = Session.getInstance(props, auth);

            Message msg = new MimeMessage(session);
            msg.setContent("Testing SMTP", "text/html");
            msg.setSubject("SMTP Test");
            msg.setFrom(new InternetAddress(senderEmail));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail));
            Transport.send(msg);
            System.out.println("Test successfully");
            return true;
        } catch (Exception ex) {
            System.out.println("Error occurred while sending.!" + ex.toString());
            return false;
        }
    }

    @Override
    public Boolean sendEmail(String emailSender, String emailRecipient, String emailSubject, String emailHtmlBody) {
        try {
            Message msg = new MimeMessage(session);
            msg.setContent(emailHtmlBody, "text/html");
            msg.setSubject(emailSubject);
            msg.setFrom(new InternetAddress(emailSender));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailRecipient));
            Transport.send(msg);
            return true;
        } catch (Exception ex) {
            System.out.println("Error occurred while sending.!" + ex.toString());
            return false;
        }
    }

}


