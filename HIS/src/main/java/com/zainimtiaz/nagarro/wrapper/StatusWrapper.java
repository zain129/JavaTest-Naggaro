package com.zainimtiaz.nagarro.wrapper;

public class StatusWrapper {
    private Long id;
    private String   name;
    private String abbreviation;
    private boolean active;
    private String colorHash;
    private String label;
    private Long value;
    private boolean systemStatus;

    public StatusWrapper() {
    }

    public StatusWrapper(Long id, String name, String abbreviation, boolean active, String colorHash,boolean status) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.active = active;
        this.colorHash = colorHash;
        this.label =name;
        this.value = id;
        this.systemStatus=status;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getColorHash() {
        return colorHash;
    }

    public void setColorHash(String colorHash) {
        this.colorHash = colorHash;
    }

    public boolean isSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(boolean systemStatus) {
        this.systemStatus = systemStatus;
    }
}
