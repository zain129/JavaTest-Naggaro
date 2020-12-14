package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.SMSTemplate;
import com.zainimtiaz.nagarro.repository.SmsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SmsTemplateService {

    @Autowired
    private SmsTemplateRepository smsTemplateRepository;


    @Transactional
    public void saveConfiguration(SMSTemplate templateData){
        SMSTemplate SMSTemplate;
        if(templateData.getId() == null){
            SMSTemplate = new SMSTemplate();
        }
        SMSTemplate = templateData;
        smsTemplateRepository.save(SMSTemplate);
    }


    public List<SMSTemplate> getAll(){
        return smsTemplateRepository.findAll();
    }

    public void delete(long id){
        smsTemplateRepository.delete(id);
    }

    public SMSTemplate getById(long id){
       return smsTemplateRepository.findOne(id);
    }
}
