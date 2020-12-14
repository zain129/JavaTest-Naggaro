package com.zainimtiaz.nagarro.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.zainimtiaz.nagarro.model.EmailConfiguration;
import com.zainimtiaz.nagarro.repository.EmailConfigurationRepository;
import com.zainimtiaz.nagarro.utill.AmazonSESUtil;
import com.zainimtiaz.nagarro.utill.IEmailUtil;
import com.zainimtiaz.nagarro.utill.SMTPUtil;
import com.zainimtiaz.nagarro.wrapper.EmailWrapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class EmailService implements ApplicationContextAware {


    private static EmailService INSTANCE;
    private EmailConfiguration configuration;
    private IEmailUtil emailUtil;
    private static ApplicationContext ctx;

    /*private EmailService(){}*/

    private void initializeDefault() {
        ApplicationContext appCtx = EmailService
                .getApplicationContext();
        EmailConfigurationRepository emailConfigurationRepository = (EmailConfigurationRepository) appCtx.getBean(EmailConfigurationRepository.class);
      //  System.out.println("test branc..."+ branchService.totalBranches());
         configuration = emailConfigurationRepository.findBySystemDefaultTrue();
        if(configuration.getServerType().equalsIgnoreCase("SES")) {
            emailUtil = AmazonSESUtil.getInstance(false);
        }
        else {
            emailUtil = SMTPUtil.getInstance(false);
        }
    }

   public static synchronized EmailService getInstance(Boolean isUpdated) {
        if(INSTANCE == null || isUpdated) {
            INSTANCE = new EmailService();

        }
        return INSTANCE;

    }

@Transactional
    public  Boolean sendEmail(EmailWrapper email) {
            initializeDefault();
    return emailUtil.sendEmail(configuration.getSenderEmail(),email.getRecepient(), email.getSubject(), email.getContent());
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

}
