package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zainimtiaz.nagarro.wrapper.DocumentWrapper;

import javax.persistence.*;
import java.io.Serializable;

/*
 * @author    : Tahir Mehmood
 * @Date      : 31-Jul-2018
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
 * @FileName  : User
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "DOCUMENT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "URL")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    private Patient patient;


    public Document() {
    }
    public Document(DocumentWrapper documentWrapper) {
        this.name =documentWrapper.getName();
        this.type = documentWrapper.getType();
        this.description = documentWrapper.getDescription();
        this.url = documentWrapper.getUrl();
    }

    public Document(String name, String type, String description, String url, Patient patient) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.url = url;
        this.patient = patient;
    }

    public Document(DocumentWrapper documentWrapper, Patient patient) {
        this(documentWrapper.getName(), documentWrapper.getType(), documentWrapper.getDescription(), documentWrapper.getUrl(), patient);
    }

    public Document(Document document, DocumentWrapper documentWrapper) {
        document.setName(documentWrapper.getName());
        document.setType(documentWrapper.getType());
        document.setDescription(documentWrapper.getDescription());
        document.setUrl(documentWrapper.getUrl());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
