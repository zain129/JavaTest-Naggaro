package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.ICDVersion;

public class ICDVersionWrapper {

    private long id;
    private String name;
    private String description;
    private boolean status;
    private long updatedOn;
    private long createdOn;
    private boolean deleted;
    private long value ;
    private String label;
    /**
     * means this object or ICD VERSION has child record
     */
    private boolean hasChild;///associated;
    private boolean selectedVersion;


    public ICDVersionWrapper() {
    }

    public ICDVersionWrapper(ICDVersion iCDVersion) {
        this.id = iCDVersion.getId();
        this.name = iCDVersion.getName();
        this.description = iCDVersion.getDescription();
        this.status = iCDVersion.isStatus();
        this.updatedOn = iCDVersion.getUpdatedOn().getTime();
        this.createdOn = iCDVersion.getCreatedOn().getTime();
        this.label = iCDVersion.getName();
        this.value = iCDVersion.getId();

        if (iCDVersion.getVersions() != null && iCDVersion.getVersions().size() > 0) {
            this.hasChild = true;
        }

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

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public boolean isSelectedVersion() {
        return selectedVersion;
    }

    public void setSelectedVersion(boolean selectedVersion) {
        this.selectedVersion = selectedVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
