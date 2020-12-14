package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.EmailTemplate;
import com.zainimtiaz.nagarro.repository.EmailTemplateRepository;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.request.EmailTemplateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 14-May-18
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
 * @FileName  : TaxService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class EmailTemplateService {

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    public List<EmailTemplateWrapper> findAllEmailTemplate(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        return emailTemplateRepository.findAllByCreatedOnNotNull(pageable);
    }

    public int countAllEmailTemplate() {
        return emailTemplateRepository.findAll().size();
    }

    @Transactional(rollbackOn = Throwable.class)
    public void deleteById(long id) {
        EmailTemplate emailTemplate = emailTemplateRepository.findOne(id);
        if (HISCoreUtil.isValidObject(emailTemplate)) {
            emailTemplateRepository.delete(emailTemplate);
        }
    }

    public boolean isDupTitle(String title) {
        List<EmailTemplate> emailTemplate = emailTemplateRepository.findAllByTitle(title);
        if (HISCoreUtil.isListEmpty(emailTemplate)) {
            return false;
        }
        return true;
    }

    public boolean isDupTitleAgainstId(EmailTemplateWrapper emailTemplateRequest) {
        EmailTemplate emailTemplate = emailTemplateRepository.findByTitleAndIdNot(
                emailTemplateRequest.getTitle(),
                emailTemplateRequest.getId());
        if (HISCoreUtil.isValidObject(emailTemplate)) {
            return true;
        }
        return false;
    }

    @Transactional(rollbackOn = Throwable.class)
    public void saveEmailTemplate(EmailTemplateWrapper createRequest) {
        EmailTemplate emailTemplate = new EmailTemplate();
        new EmailTemplateWrapper(emailTemplate, createRequest);
        emailTemplateRepository.save(emailTemplate);
    }

    @Transactional(rollbackOn = Throwable.class)
    public EmailTemplate updateEmailTemplate(EmailTemplateWrapper createRequest) {
        EmailTemplate emailTemplate = emailTemplateRepository.findOne(createRequest.getId());
        if (HISCoreUtil.isValidObject(emailTemplate)) {
            new EmailTemplateWrapper(emailTemplate, createRequest);
            emailTemplateRepository.save(emailTemplate);
            return emailTemplate;
        }
        return null;
    }

    public EmailTemplateWrapper getEmailTemplateById(long id) {
        return emailTemplateRepository.getEmailTemplateById(id);
    }

    public List<EmailTemplateWrapper> searchEmailTemplateByTitle(int page, int pageSize, String title) {
        Pageable pageable = new PageRequest(page, pageSize);
        return emailTemplateRepository.findAllByTitleContaining(title, pageable);
    }

    public int countSearchEmailTemplateByTitle(String title) {
        return emailTemplateRepository.findAllByTitleContaining(title).size();
    }

}