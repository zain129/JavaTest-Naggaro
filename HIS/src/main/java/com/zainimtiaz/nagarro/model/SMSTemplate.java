package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "SMS_TEMPLATE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SMSTemplate extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "SMS_TEMPLATE",length = 4000)
    private String smsTemplate;

    @Column(name = "IS_ACTIVE")
    private boolean active;


    public SMSTemplate() {
    }

    @Override
    public String toString() {
        return super.toString();
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
