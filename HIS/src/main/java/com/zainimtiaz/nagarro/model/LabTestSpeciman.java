package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "LAB_TEST_SPECIMAN")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LabTestSpeciman extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;



    @NaturalId
    @Column(name = "SPECIMAN_ID", unique = true, nullable = false, updatable = false)
    private String specimanId;


    @Column(name = "TEST_CODE")
    private String testCode;

    @Column(name = "TEST_NAME")
    private String testName;

    @Column(name = "MIN_NORMAL_RANGE")
    private String minNormalRange;

    @Column(name = "MAX_NORMAL_RANGE")
    private String maxNormalRange;

    @Column(name = "DESCRIPTION")
    private String description;



    @Column(name = "UNIT")
    private String unit;




    public LabTestSpeciman() {
    }

    public LabTestSpeciman(LabTestSpeciman labTestSpeciman) {
        this.specimanId=labTestSpeciman.getSpecimanId();
        this.setId(labTestSpeciman.getId());
        this.testCode = labTestSpeciman.getTestCode();
        this.testName = labTestSpeciman.getTestName();
        this.maxNormalRange = labTestSpeciman.getMaxNormalRange();
        this.minNormalRange = labTestSpeciman.getMinNormalRange();
        this.description = labTestSpeciman.getDescription();
        this.unit=labTestSpeciman.getUnit();
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getMinNormalRange() {
        return minNormalRange;
    }

    public void setMinNormalRange(String minNormalRange) {
        this.minNormalRange = minNormalRange;
    }

    public String getMaxNormalRange() {
        return maxNormalRange;
    }

    public void setMaxNormalRange(String maxNormalRange) {
        this.maxNormalRange = maxNormalRange;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public String getSpecimanId() {
        return specimanId;
    }

    public void setSpecimanId(String specimanId) {
        this.specimanId = specimanId;
    }
}
