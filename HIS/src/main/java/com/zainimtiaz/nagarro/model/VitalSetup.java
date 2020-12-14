package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "VITAL_SETUP")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VitalSetup  extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "Name")
    private String name;

    @Column(name = "UNIT")
    private String unit;

    @Column(name = "STANDARD_VALUE")
    private String standardValue;

    @Column(name = "STATUS")
    private String status;

    public VitalSetup() {
    }


    public VitalSetup(VitalSetup vitalSetup) {
        this.setId(vitalSetup.getId());
        this.name = vitalSetup.getName();
        this.unit = vitalSetup.getUnit();
        this.standardValue = vitalSetup.getStandardValue();
        this.status = vitalSetup.getStatus();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(String standardValue) {
        this.standardValue = standardValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
