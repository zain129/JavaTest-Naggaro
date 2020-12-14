package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.City;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;

import java.util.Date;

/**
 * Created by jamal on 11/8/2018.
 */
public class CityWrapper extends BaseWrapper {

    private String name;
    private CountryWrapper countryWrapper;
    private StateWrapper stateWrapper;
    private boolean status;


    public CityWrapper() {
    }

    public CityWrapper(City c) {
        super(c.getId(),
                HISCoreUtil.convertDateToString(c.getCreatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd),
                HISCoreUtil.convertDateToString(c.getUpdatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd));
        this.name = c.getName();
        this.status = c.isStatus();
        this.stateWrapper = new StateWrapper();
        new StateWrapper(this.stateWrapper, c.getState());
    }

    public CityWrapper(Long id, Date createdOn, Date updatedOn,
                       String name, CountryWrapper countryWrapper, StateWrapper stateWrapper, boolean status) {
        super(id,
                HISCoreUtil.convertDateToString(createdOn, HISConstants.DATE_FORMATE_YYY_MM_dd),
                HISCoreUtil.convertDateToString(updatedOn, HISConstants.DATE_FORMATE_YYY_MM_dd));
        this.name = name;
        this.countryWrapper = countryWrapper;
        this.stateWrapper = stateWrapper;
        this.status = status;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryWrapper getCountryWrapper() {
        return countryWrapper;
    }

    public void setCountryWrapper(CountryWrapper countryWrapper) {
        this.countryWrapper = countryWrapper;
    }

    public StateWrapper getStateWrapper() {
        return stateWrapper;
    }

    public void setStateWrapper(StateWrapper stateWrapper) {
        this.stateWrapper = stateWrapper;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
