package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.utill.HISCoreUtil;

import java.util.Date;

/**
 * Created by jamal on 10/29/2018.
 */
public class CurrencyWrapper extends BaseWrapper {

    String ios;
    String symbol;
    String description;
    boolean systemDefault;
    boolean status;
    boolean hasChild;

    public CurrencyWrapper() {
    }

    public CurrencyWrapper(Long id, Date createdOn, Date updatedOn,
                           String ios, String symbol, String description, boolean systemDefault, boolean status) {
        super(id,
                HISCoreUtil.convertDateToString(createdOn),
                HISCoreUtil.convertDateToString(updatedOn));
        this.ios = ios;
        this.symbol = symbol;
        this.description = description;
        this.systemDefault = systemDefault;
        this.status = status;
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

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }
}
