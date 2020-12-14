package com.zainimtiaz.nagarro.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "DRUG_MANUFACTURER")
public class DrugManufacturer extends BaseEntity {

    @NaturalId
    @Column(name = "drug_manufacturer_natural")
    private String drugManufacturerNaturalId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ACTIVE")
    private Boolean status;
    @OneToMany(mappedBy = "drugManufacturer")
    private List<Drug> drug;

    public DrugManufacturer() {
    }

    public DrugManufacturer(Long id, String drugManufacturerNaturalId, String name, Boolean status) {
        this.setId(id);
        this.drugManufacturerNaturalId = drugManufacturerNaturalId;
        this.name = name;
        this.status = status;
    }

    public String getDrugManufacturerNaturalId() {
        return drugManufacturerNaturalId;
    }

    public void setDrugManufacturerNaturalId(String drugManufacturerNaturalId) {
        this.drugManufacturerNaturalId = drugManufacturerNaturalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Drug> getDrug() {
        return drug;
    }

    public void setDrug(List<Drug> drug) {
        this.drug = drug;
    }
}

