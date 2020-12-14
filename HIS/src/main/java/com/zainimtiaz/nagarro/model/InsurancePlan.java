package com.zainimtiaz.nagarro.model;

import com.zainimtiaz.nagarro.wrapper.InsuranceProfileWrapper;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "INSURANCE_PLAN")
public class InsurancePlan extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS", columnDefinition = "boolean default true", nullable = false)
    private boolean status;



    public InsurancePlan() {
    }

    public InsurancePlan(InsuranceProfileWrapper createRequest) {
        this.name = createRequest.getName();
        this.description = createRequest.getDescription();
        this.status = createRequest.isStatus();
    }


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



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
