package com.zainimtiaz.nagarro.wrapper;

import com.zainimtiaz.nagarro.model.Tax;
import com.zainimtiaz.nagarro.utill.DateTimeUtil;
import com.zainimtiaz.nagarro.utill.HISConstants;

import java.util.Date;

/**
 * Created by jamal on 7/31/2018.
 */
public class TaxWrapper {
    private long id;
    private String name;
    private String description;
    private double rate;



    private String dateFormat;
   // private String dateFormat;

    public String getStrfromDate() {
        return strfromDate;
    }

    public void setStrfromDate(String strfromDate) {
        this.strfromDate = strfromDate;
    }

    public String getStrtoDate() {
        return strtoDate;
    }

    public void setStrtoDate(String strtoDate) {
        this.strtoDate = strtoDate;
    }

    private String strfromDate;
    private String strtoDate;
    private boolean active;
    private long value;
    private String label;
    private Date fromDate;
    private Date toDate;/**
     * if child record found then true
     * it not then false
     */
    private boolean hasChild;

    public TaxWrapper() {
    }


    public TaxWrapper(Tax tax) {
        this.id = tax.getId();
        this.name = tax.getName();
        this.description = tax.getDescription();
        this.rate = tax.getRate();
        this.strfromDate = DateTimeUtil.getFormattedDateFromDate(tax.getFromDate(), HISConstants.DATE_FORMATE_THREE);
        this.strtoDate = DateTimeUtil.getFormattedDateFromDate(tax.getToDate(), HISConstants.DATE_FORMATE_THREE);
        this.active = tax.getActive();
        this.value = tax.getId();
        this.label = tax.getName();
        if (tax.getMedicalServices() != null && tax.getMedicalServices().size() > 0) {
            this.hasChild = true;
        }

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setId(long id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
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

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

}
