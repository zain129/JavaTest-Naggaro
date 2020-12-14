package com.zainimtiaz.nagarro.wrapper.request;

import com.zainimtiaz.nagarro.model.EmailTemplate;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.BaseWrapper;

import java.util.Date;

public class EmailTemplateWrapper extends BaseWrapper {
    private String title;
    private String subject;
    private String type;
    private String emailTemplate;
    private boolean active;


    public EmailTemplateWrapper() {
    }

    public EmailTemplateWrapper(Long id, Date createdOn, Date updatedOn) {
        super(id,
                HISCoreUtil.convertDateToString(createdOn),
                HISCoreUtil.convertDateToString(updatedOn));
    }

    public EmailTemplateWrapper(Long id, Date createdOn, Date updatedOn, String title, String subject, String type, String emailTemplate, boolean active) {
        super(id,
                HISCoreUtil.convertDateToString(createdOn),
                HISCoreUtil.convertDateToString(updatedOn));
        this.title = title;
        this.subject = subject;
        this.type = type;
        this.emailTemplate = emailTemplate;
        this.active = active;

    }

    public EmailTemplateWrapper(EmailTemplate emailTemplate, EmailTemplateWrapper createRequest) {
        emailTemplate.setTitle(createRequest.getTitle());
        emailTemplate.setSubject(createRequest.getSubject());
        emailTemplate.setType(createRequest.getType());
        emailTemplate.setEmailTemplate(createRequest.getEmailTemplate());
        emailTemplate.setActive(createRequest.isActive());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}