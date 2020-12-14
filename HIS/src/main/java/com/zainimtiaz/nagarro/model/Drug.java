package com.zainimtiaz.nagarro.model;

import com.zainimtiaz.nagarro.wrapper.DrugWrapper;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jamal on 10/22/2018.
 */
@Entity
@Table(name = "drug")
public class Drug extends BaseEntity {

    @NaturalId
    @Column(name = "DRUG_NATURAL")
    private String drugNaturalId;
    @Column(name = "DRUG_NAME")
    private String drugName;
    @Column(name = "GENERIC_NAME")
    private String GenericName;
    @Column(name = "COMPANY_NAME")
    private String companyName;
    private String route;
    @ElementCollection
    @Column(name = "STRENGTHS")
    private List<String> strengths;
    private String uOM;
   // private String origin;
    private boolean active;
    @Column(name = "Drug_info")
    private String druginfo;
    @ManyToOne
    @JoinColumn(name = "Drug_Manufacturer_ID")
    private DrugManufacturer drugManufacturer;

    public DrugManufacturer getDrugManufacturer() {
        return drugManufacturer;
    }

    public void setDrugManufacturer(DrugManufacturer drugManufacturer) {
        this.drugManufacturer = drugManufacturer;
    }

    public Drug() {
    }

    /**
     * for save
     */
    public Drug(DrugWrapper drugWrapper) {
        this.drugNaturalId = drugWrapper.getDrugNaturalId();
        this.drugName = drugWrapper.getDrugName();
        this.GenericName = drugWrapper.getGenericName();
        this.companyName = drugWrapper.getCompanyName();
        this.route = drugWrapper.getRoute();
        this.strengths = drugWrapper.getStrengths();
        this.uOM = drugWrapper.getuOM();
      //  this.country = drugWrapper.getSelectedCountry();
        this.druginfo=drugWrapper.getDrugInfo();
        this.active = drugWrapper.isActive();
    }
    /**
     * for update
     */
    public Drug(Drug drug, DrugWrapper drugWrapper) {
        drug.drugNaturalId = drugWrapper.getDrugNaturalId();
        drug.drugName = drugWrapper.getDrugName();
        drug.GenericName = drugWrapper.getGenericName();
        drug.companyName = drugWrapper.getCompanyName();
        drug.route = drugWrapper.getRoute();
        drug.strengths = drugWrapper.getStrengths();
        drug.uOM = drugWrapper.getuOM();
        drug.active = drugWrapper.isActive();
        if (drugWrapper.getDrugInfo() != null && !drugWrapper.getDrugInfo().trim().equals("")) {
            drug.druginfo = ((drugWrapper.getDrugInfo().contains("http:") || drugWrapper.getDrugInfo().contains("https:"))
                    ? drugWrapper.getDrugInfo()
                    : ("http://" + drugWrapper.getDrugInfo()));
        }
    }

    public String getDrugNaturalId() {
        return drugNaturalId;
    }

    public void setDrugNaturalId(String drugNaturalId) {
        this.drugNaturalId = drugNaturalId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getGenericName() {
        return GenericName;
    }

    public void setGenericName(String genericName) {
        GenericName = genericName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public List<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    public String getuOM() {
        return uOM;
    }

    public void setuOM(String uOM) {
        this.uOM = uOM;
    }
/*
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }*/

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDruginfo() {
        return druginfo;
    }

    public void setDruginfo(String druginfo) {
        this.druginfo = druginfo;
    }
}
