package com.zainimtiaz.nagarro.wrapper;

import com.sd.his.model.*;
import com.zainimtiaz.nagarro.model.InsurancePlan;

import java.util.List;


public class InsuranceProfileWrapper  {



    private long id;
    private String code;
    private String problem;
    private String infoURL;
    private boolean status;
    private boolean deleted;
    private long updatedOn;
    private long createdOn;
    private boolean checkedCode;
    private String description;
    private String descriptionCodeVersion;
    private ICDVersionWrapper iCDVersion;


    private String name;

    private boolean hasChild;///associated;
    private List<ICDVersionWrapper> selectedVersions;
    private String checkedVersionCount;
    private boolean versionCountUnique;
    private long value;
    private String label;


    public InsuranceProfileWrapper() {
    }

    public InsuranceProfileWrapper(InsurancePlan icd) {
        this.id = icd.getId();
        this.name=icd.getName();
        this.status = icd.isStatus();
        this.description = icd.getDescription();
    }


    public InsuranceProfileWrapper(long id,String name,String description,boolean status) {
        this.id = id;
        this.name=name;
        this.status = status;
        this.description = description;
    }

    public InsuranceProfileWrapper(long id,String name,boolean status,String description) {
        this.id = id;
        this.name=name;
        this.status = status;
        this.description = description;
    }




    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }



    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }



    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }



    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public ICDVersionWrapper getICDVersionWrapper() {
        return iCDVersion;
    }

    public void setICDVersionWrapper(ICDVersionWrapper iCDVersionWrapper) {
        this.iCDVersion = iCDVersionWrapper;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCheckedCode() {
        return checkedCode;
    }

    public void setCheckedCode(boolean checkedCode) {
        this.checkedCode = checkedCode;
    }

    public String getDescriptionCodeVersion() {
        return descriptionCodeVersion;
    }

    public void setDescriptionCodeVersion(String descriptionCodeVersion) {
        this.descriptionCodeVersion = descriptionCodeVersion;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public ICDVersionWrapper getiCDVersion() {
        return iCDVersion;
    }

    public void setiCDVersion(ICDVersionWrapper iCDVersion) {
        this.iCDVersion = iCDVersion;
    }

    public List<ICDVersionWrapper> getSelectedVersions() {
        return selectedVersions;
    }

    public void setSelectedVersions(List<ICDVersionWrapper> selectedVersions) {
        this.selectedVersions = selectedVersions;
    }

    public String getCheckedVersionCount() {
        return checkedVersionCount;
    }

    public void setCheckedVersionCount(String checkedVersionCount) {
        this.checkedVersionCount = checkedVersionCount;
    }

    public boolean isVersionCountUnique() {
        return versionCountUnique;
    }

    public void setVersionCountUnique(boolean versionCountUnique) {
        this.versionCountUnique = versionCountUnique;
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

    public long getId() {
        return id;
    }


}
