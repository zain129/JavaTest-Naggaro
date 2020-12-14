package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.State;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
public class StateWrapper extends BaseWrapper {

    private String name;
    private boolean status;


    public StateWrapper() {
    }

    public StateWrapper(Long id, Date createdOn, Date updatedOn,
                        String name, CountryWrapper countryWrapper, List<CityWrapper> cityWrappers, boolean status) {
        super(id,
                HISCoreUtil.convertDateToString(createdOn, HISConstants.DATE_FORMATE_YYY_MM_dd),
                HISCoreUtil.convertDateToString(updatedOn, HISConstants.DATE_FORMATE_YYY_MM_dd));
        this.name = name;
        this.status = status;
    }

    public StateWrapper(State state) {
        super(state.getId(),
                HISCoreUtil.convertDateToString(state.getCreatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd),
                HISCoreUtil.convertDateToString(state.getUpdatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd));
        this.name = state.getName();
        this.status = state.isStatus();
//        this.countryWrapper = new CountryWrapper();
//        new CountryWrapper(this.countryWrapper, state.getCountry());
        /*this.countryWrapper = state.getCountry();
        this.cityWrappers = cityWrappers;*/
    }

    public StateWrapper(StateWrapper stateWrapper, State state) {
        stateWrapper.setId(state.getId());
        stateWrapper.name = state.getName();
        stateWrapper.status = state.isStatus();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public CountryWrapper getCountryWrapper() {
//        return countryWrapper;
//    }
//
//    public void setCountryWrapper(CountryWrapper countryWrapper) {
//        this.countryWrapper = countryWrapper;
//    }
//
//    public List<CityWrapper> getCityWrappers() {
//        return cityWrappers;
//    }
//
//    public void setCityWrappers(List<CityWrapper> cityWrappers) {
//        this.cityWrappers = cityWrappers;
//    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
