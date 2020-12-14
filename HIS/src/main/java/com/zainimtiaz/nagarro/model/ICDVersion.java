package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zainimtiaz.nagarro.wrapper.ICDVersionWrapper;

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
 * @FileName  : ICDVersion
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "ICD_VERSION")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ICDVersion extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS", columnDefinition = "boolean default true", nullable = false)
    private boolean status;

    @JsonIgnore
    @OneToMany(targetEntity = ICDCodeVersion.class, mappedBy = "version", cascade = {CascadeType.ALL})
    private List<ICDCodeVersion> versions;

    @OneToMany(mappedBy = "icdVersion")
    private List<Problem> problems;

    public ICDVersion() {
    }

    public ICDVersion(ICDVersionWrapper createRequest) {
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
        this.status = createRequest.isStatus();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ICDCodeVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<ICDCodeVersion> versions) {
        this.versions = versions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }
}
