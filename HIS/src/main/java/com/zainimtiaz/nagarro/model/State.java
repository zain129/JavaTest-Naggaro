package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jamal on 11/7/2018.
 */
@Entity
@Table(name = "STATE")
public class State extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    @JsonBackReference
    private Country country;

    @OneToMany(targetEntity = City.class, mappedBy = "state")
    private List<City> cities;

    @OneToMany(targetEntity = Branch.class, mappedBy = "state")
    private List<Branch> branch;

    @Column(name = "STATUS")
    private boolean status;

    public State() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Branch> getBranch() {
        return branch;
    }

    public void setBranch(List<Branch> branch) {
        this.branch = branch;
    }
}
