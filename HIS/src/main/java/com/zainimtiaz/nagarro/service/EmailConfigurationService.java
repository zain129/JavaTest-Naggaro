package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.EmailConfiguration;
import com.zainimtiaz.nagarro.repository.EmailConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmailConfigurationService {

    @Autowired
    private EmailConfigurationRepository emailConfigurationRepository;

    @Transactional
    public EmailConfiguration saveSMTPSConfiguration(EmailConfiguration smtpRequestWrapper){

        EmailConfiguration smtpObj=emailConfigurationRepository.findByServerType(smtpRequestWrapper.getServerType());

        if(smtpObj !=null){
            long id= smtpObj.getId();
            smtpObj = smtpRequestWrapper;
            smtpObj.setId(id);
        }else{
            smtpObj = new EmailConfiguration();
            smtpObj = smtpRequestWrapper;
            smtpObj.setUpdatedOn(new Date());
        }
      return  emailConfigurationRepository.save(smtpObj);
    }


    @Transactional
    public EmailConfiguration saveSESConfiguration(EmailConfiguration sesRequestWrapper){

        EmailConfiguration sesObj=emailConfigurationRepository.findByServerType(sesRequestWrapper.getServerType());

        if(sesObj !=null){
            long id= sesObj.getId();
            sesObj = sesRequestWrapper;
            sesObj.setId(id);
        }else{
            sesObj = new EmailConfiguration();
            sesObj = sesRequestWrapper;
            sesObj.setUpdatedOn(new Date());
        }
      return emailConfigurationRepository.save(sesObj);
    }

    @Transactional
    public List<EmailConfiguration> getEmailConfigurations(){
        return emailConfigurationRepository.findAll();
    }
}
