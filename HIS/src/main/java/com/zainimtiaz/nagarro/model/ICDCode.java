package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zainimtiaz.nagarro.wrapper.ICDCodeCreateRequest;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/*
 * @author    : Irfan Nasim
 * @Date      : 26-Apr-18
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
 * @FileName  : ICDCode
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "ICD_CODE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ICDCode extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PROBLEM")
    private String problem;

    @Column(name = "INFO_URL",length = 4000)
    private String infoURL;

    @Column(name = "STATUS", columnDefinition = "boolean default true", nullable = false)
    private Boolean status;

    @Column(name = "DESCRIPTION")
    private String description;


    @JsonIgnore
    @OneToMany(targetEntity = ICDCodeVersion.class, mappedBy = "icd")
    private List<ICDCodeVersion> versions;//// codeVersions

    @JsonIgnore
    @OneToMany(targetEntity = Problem.class, mappedBy = "icdCode")
    private List<Problem> problems;


    public ICDCode() {
    }

    public ICDCode(ICDCodeCreateRequest createRequest) {
        this.code = createRequest.getCode();
        this.problem = createRequest.getProblem();
        this.infoURL = createRequest.getInfoURL();
        this.status = createRequest.isStatus();
        this.description = createRequest.getDescription();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ICDCodeVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<ICDCodeVersion> versions) {
        this.versions = versions;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getInfoURL() {
        return infoURL;
    }

    public void setInfoURL(String infoURL) {
        this.infoURL = infoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}