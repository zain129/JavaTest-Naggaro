package com.zainimtiaz.nagarro.wrapper;


import java.util.List;

/*
 * @author    : Qari Jamal
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
 * @Package   : com.sd.his.wrapper
 * @FileName  : ICDCodeCreateRequest
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
public class ICDCodeCreateRequest {

    long id;
    boolean status;
    String code;
    String problem;//title replaced by problem
    private String infoURL;
    long updatedOn;
    long createdOn;
    String description;
    List<ICDVersionWrapper> selectedVersions;

    public ICDCodeCreateRequest() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ICDVersionWrapper> getSelectedVersions() {
        return selectedVersions;
    }

    public void setSelectedVersions(List<ICDVersionWrapper> selectedVersions) {
        this.selectedVersions = selectedVersions;
    }

    public String getInfoURL() {
        return infoURL;
    }

    public void setInfoURL(String infoURL) {
        this.infoURL = infoURL;
    }
}
