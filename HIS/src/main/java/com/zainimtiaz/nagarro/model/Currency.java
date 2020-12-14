package com.zainimtiaz.nagarro.model;

import com.zainimtiaz.nagarro.wrapper.CurrencyWrapper;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by jamal on 10/29/2018.
 */
@Entity
public class Currency extends BaseEntity {

    String ios;
    String symbol;
    @Column(name = "description", length = 4000)
    String description;
    @Column(name = "SYSTEM_DEFAULT")
    boolean systemDefault;
    boolean status;

    public Currency() {
    }

    public Currency(CurrencyWrapper currencyWrapper) {
        this.ios = currencyWrapper.getIos();
        this.symbol = currencyWrapper.getSymbol();
        this.description = currencyWrapper.getDescription();
        this.systemDefault = currencyWrapper.isSystemDefault();
        this.status = currencyWrapper.isStatus();
    }

    public Currency(Currency currency, CurrencyWrapper currencyWrapper) {
        currency.ios = currencyWrapper.getIos();
        currency.symbol = currencyWrapper.getSymbol();
        currency.description = currencyWrapper.getDescription();
        currency.systemDefault = currencyWrapper.isSystemDefault();
        currency.status = currencyWrapper.isStatus();
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSystemDefault() {
        return systemDefault;
    }

    public void setSystemDefault(boolean systemDefault) {
        this.systemDefault = systemDefault;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
