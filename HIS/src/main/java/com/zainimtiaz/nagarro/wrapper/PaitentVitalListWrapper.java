package com.zainimtiaz.nagarro.wrapper;

import java.util.List;

public class PaitentVitalListWrapper  extends BaseWrapper{

    private String name;
    private boolean status;



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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    private String unit;

    private List<VitalWrapper> listOfVital;


    public List<VitalWrapper> getListOfVital() {
        return listOfVital;
    }

    public void setListOfVital(List<VitalWrapper> listOfVital) {
        this.listOfVital = listOfVital;
    }


    public PaitentVitalListWrapper(List<VitalWrapper> listOfVital,String name,boolean status,String unit) {
        this.name = name;
        this.status = status;
        this.unit = unit;
        this.listOfVital = listOfVital;
    }

    public PaitentVitalListWrapper(String name,String unit,boolean status,List<VitalWrapper> listOfVital) {
        this.name = name;
        this.status = status;
        this.unit = unit;
        this.listOfVital = listOfVital;
    }


    public PaitentVitalListWrapper(){}
}
