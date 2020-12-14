package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.Drug;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by jamal on 10/22/2018.
 */
public class DrugWrapper extends BaseWrapper {

    private String drugNaturalId;
    private String drugName;
    private String genericName;
    private String companyName;
    private String route;
    private List<String> strengths;
    private String strength = "";
    private String uOM;
    private String drugMaker;
    private String drugInfo;
    private boolean active = true;
    private boolean hasChild;
    private String drugMakerView;
    Map<String, Object> addInfo;

    public DrugWrapper() {
    }

    public DrugWrapper(String drugName, String route) {
        this.drugName = drugName;
        this.route = route;
    }

    public DrugWrapper(Drug drug) {
        super(drug.getId(),
                HISCoreUtil.convertDateToString(drug.getCreatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd),
                HISCoreUtil.convertDateToString(drug.getUpdatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd));
        this.drugNaturalId = drug.getDrugNaturalId();
        this.drugName = drug.getDrugName();
        this.genericName = drug.getGenericName();
        this.companyName = drug.getCompanyName();
        this.uOM = drug.getuOM();
        this.route = drug.getRoute();
        this.strengths = drug.getStrengths();
        this.active = drug.isActive();
        this.hasChild = false;
        this.drugInfo = drug.getDruginfo();

        if (drug.getDrugManufacturer() != null) {
            this.drugMakerView = drug.getDrugManufacturer().getName();
            this.drugMaker = drug.getDrugManufacturer().getName();
        }

        if (drug.getStrengths() != null && drug.getStrengths().size() > 0) {
            for (String s : drug.getStrengths()) {
                this.strength = this.strength.concat(s + ",");
            }
            this.strength = this.strength.substring(0, this.getStrength().length() - 1);
        }
    }

    public String getDrugMakerView() {
        return drugMakerView;
    }

    public void setDrugMakerView(String drugMakerView) {
        this.drugMakerView = drugMakerView;
    }

    public String getDrugInfo() {
        return drugInfo;
    }

    public void setDrugInfo(String drugInfo) {
        this.drugInfo = drugInfo;
    }

    public String getDrugMaker() {
        return drugMaker;
    }

    public void setDrugMaker(String drugMaker) {
        this.drugMaker = drugMaker;
    }

    public Map<String, Object> getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(Map<String, Object> addInfo) {
        this.addInfo = addInfo;
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
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }
}
