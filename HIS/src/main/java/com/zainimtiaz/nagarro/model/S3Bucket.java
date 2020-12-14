package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/*
 * @author    : Tahir Mehmood
 * @Date      : 16-Jul-18
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
 * @FileName  : S3Bucket
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "S3_BUCKET")
@JsonIgnoreProperties(ignoreUnknown = true)
public class S3Bucket extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ACCESS_KEY", unique = true)
    private String accessKey;

    @Column(name = "SECRET_KEY", unique = true)
    private String secretKey;

    @Column(name = "ACCESS_PROTOCOL")
    private String accessProtocol;

    @Column(name = "PUBLIC_BASE_URL")
    private String publicBaseURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    private Organization organization;

    @Column(name = "IS_ACTIVE", columnDefinition = "boolean default true", nullable = false)
    private Boolean active;

        @Column(name = "SYS_DEFAULT", columnDefinition = "boolean default false", nullable = false)
        private Boolean sysDefault;

    public S3Bucket(String name, String description, String accessKey, String secretKey, String accessProtocol, String publicBaseURL, Boolean active, Boolean sysDefault, Organization organization) {
        this.name = name;
        this.description = description;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.accessProtocol = accessProtocol;
        this.publicBaseURL = publicBaseURL;
        this.active = active;
        this.sysDefault = sysDefault;
        this.organization = organization;
    }

    public S3Bucket(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAccessProtocol() {
        return accessProtocol;
    }

    public void setAccessProtocol(String accessProtocol) {
        this.accessProtocol = accessProtocol;
    }

    public String getPublicBaseURL() {
        return publicBaseURL;
    }

    public void setPublicBaseURL(String publicBaseURL) {
        this.publicBaseURL = publicBaseURL;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getSysDefault() {
        return sysDefault;
    }

    public void setSysDefault(Boolean sysDefault) {
        this.sysDefault = sysDefault;
    }
}
