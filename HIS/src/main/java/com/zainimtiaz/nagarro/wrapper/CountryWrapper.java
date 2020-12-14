package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.Country;
import com.zainimtiaz.nagarro.utill.HISConstants;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
public class CountryWrapper extends BaseWrapper {

    private String name;
    private String iso3;
    private String iso2;
    private String countryCode;
    private String phoneCode;
    private String capital;
    private String currency;
    private boolean status;

    private List<StateWrapper> stateWrappers;

    public CountryWrapper() {
    }

    public CountryWrapper(Long id, Date createdOn, Date updatedOn,
                          String name, String iso3, String iso2, String countryCode, String phoneCode, String capital,
                          String currency, boolean status, List<StateWrapper> stateWrappers) {
        super(id,
                HISCoreUtil.convertDateToString(createdOn, HISConstants.DATE_FORMATE_YYY_MM_dd),
                HISCoreUtil.convertDateToString(updatedOn, HISConstants.DATE_FORMATE_YYY_MM_dd));
        this.name = name;
        this.iso3 = iso3;
        this.iso2 = iso2;
        this.countryCode = countryCode;
        this.phoneCode = phoneCode;
        this.capital = capital;
        this.currency = currency;
        this.status = status;
        this.stateWrappers = stateWrappers;
    }

    public CountryWrapper(Country c) {
        super(c.getId(),
                HISCoreUtil.convertDateToString(c.getCreatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd),
                HISCoreUtil.convertDateToString(c.getUpdatedOn(), HISConstants.DATE_FORMATE_YYY_MM_dd));
        this.name = c.getName();
        this.iso3 = c.getIso3();
        this.iso2 = c.getIso2();
        this.countryCode = c.getCountryCode();
        this.phoneCode = c.getPhoneCode();
        this.capital = c.getCapital();
        this.currency = c.getCurrency();
        this.status = c.isStatus();
    }

    public CountryWrapper(CountryWrapper countryWrapper, Country country) {
        countryWrapper.name = country.getName();
        countryWrapper.iso3 = country.getIso3();
        countryWrapper.iso2 = country.getIso2();
        countryWrapper.countryCode = country.getCountryCode();
        countryWrapper.phoneCode = country.getPhoneCode();
        countryWrapper.capital = country.getCapital();
        countryWrapper.currency = country.getCurrency();
        countryWrapper.status = country.isStatus();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<StateWrapper> getStateWrappers() {
        return stateWrappers;
    }

    public void setStateWrappers(List<StateWrapper> stateWrappers) {
        this.stateWrappers = stateWrappers;
    }
}
