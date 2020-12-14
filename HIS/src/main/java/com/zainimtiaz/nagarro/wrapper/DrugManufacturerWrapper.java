package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.DrugManufacturer;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;

public class DrugManufacturerWrapper extends BaseWrapper {

    private String drugManufacturerNaturalId;
    private String name;
    private Boolean status;

    public DrugManufacturerWrapper() {
    }

    public DrugManufacturerWrapper(Long id, String drugManufacturerNaturalId, String name, Boolean status) {
        this.setId(id);
        this.drugManufacturerNaturalId = drugManufacturerNaturalId;
        this.name = name;
        this.status = status;
    }

    public DrugManufacturerWrapper(DrugManufacturer drugManufacturer) {
        super(drugManufacturer.getId(),
                HISCoreUtil.convertDateToString(drugManufacturer.getCreatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd),
                HISCoreUtil.convertDateToString(drugManufacturer.getUpdatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd));
        this.drugManufacturerNaturalId = drugManufacturer.getDrugManufacturerNaturalId();
        this.name = drugManufacturer.getName();
        this.status = drugManufacturer.getStatus();
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
}
