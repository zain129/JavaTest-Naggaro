package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/*
 * @author    : Muhammad Jamal
 * @Date      : 21-May-18
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
 * @Package   : com.sd.his.model
 * @FileName  : EmailTemplate
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "EMAIL_TEMPLATE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailTemplate extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "TITLE")
    private String title;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "EMAIL_TEMPLATE",length = 4000)
    private String emailTemplate;

    @Column(name = "IS_ACTIVE")
    private boolean active;


    public EmailTemplate() {
    }

    @Override
    public String toString() {
        return super.toString();
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}