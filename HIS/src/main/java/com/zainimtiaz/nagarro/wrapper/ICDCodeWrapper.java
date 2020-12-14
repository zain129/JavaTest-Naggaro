package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.ICDCode;
import com.zainimtiaz.nagarro.model.ICDCodeVersion;

import java.util.List;


public class ICDCodeWrapper {


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
    /**
     * means this object or ICD CODE has child record
     */
    private boolean hasChild;///associated;
    private List<ICDVersionWrapper> selectedVersions;
    private String checkedVersionCount;
    private boolean versionCountUnique;
    private long value;
    private String label;


    public ICDCodeWrapper() {
    }

    public ICDCodeWrapper(ICDCode icd) {
        this.id = icd.getId();
        this.code = icd.getCode();
        this.problem = icd.getProblem();
        this.infoURL = icd.getInfoURL();
        this.status = icd.getStatus();
        this.createdOn = icd.getCreatedOn().getTime();
        this.updatedOn = icd.getUpdatedOn().getTime();
        this.description = icd.getDescription();
        this.label = code;
        this.value = id;

        if (icd.getVersions() != null) {
            long versionCount = icd.getVersions().size();
            if (versionCount == 1) {
                this.checkedVersionCount = icd.getVersions().get(0).getVersion().getName() + "";
                this.versionCountUnique = true;
            } else if (versionCount > 1) {
                this.checkedVersionCount = versionCount + "";
            }
        }
        this.hasChild = (icd.getProblems() != null && icd.getProblems().size() > 0);
    }

    public ICDCodeWrapper(ICDCodeVersion icdCodeVersion, ICDCode icd) {
        this.id = icd.getId();
        this.code = icd.getCode();
        this.problem = icd.getProblem();
        this.infoURL = icd.getInfoURL();
        this.status = icd.getStatus();
        this.createdOn = icd.getCreatedOn().getTime();
        this.updatedOn = icd.getUpdatedOn().getTime();
        this.description = icd.getDescription();
        this.descriptionCodeVersion = icdCodeVersion.getDescription();
        this.label = code;
        this.value = id;
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

    public long getId() {
        return id;
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
}
