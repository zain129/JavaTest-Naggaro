package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/*
 * @author    : Tahir Mehmood
 * @Date      : 16-Jul-18
 * @version   : ver. 1.0.0
 * 
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________ 
 *	
 * 
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.model
 * @FileName  : Prefix
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Entity
@Table(name = "PREFIX")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Prefix extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "PREFIX")
    private String name;

    @Column(name = "MODULE")
    private String module;

    @Column(name = "START_VALUE")
    private Long startValue;

    @Column(name = "CURRENT_VALUE")
    private Long currentValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    private Organization organization;

    public Prefix() {}

    public Prefix(String name, String module, Long startValue, Long currentValue, Organization organization) {
        this.name = name;
        this.module = module;
        this.startValue = startValue;
        this.currentValue = currentValue;
        this.organization = organization;
    }

    public Prefix(Prefix prefix) {
        this.setId(prefix.getId());
        this.name = prefix.getName();
        this.module = prefix.module;
        this.startValue = prefix.getStartValue();
        this.currentValue = prefix.getCurrentValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartValue() {
        return startValue;
    }

    public void setStartValue(Long startValue) {
        this.startValue = startValue;
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
