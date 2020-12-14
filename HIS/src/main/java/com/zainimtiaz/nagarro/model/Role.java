package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zainimtiaz.nagarro.wrapper.RoleWrapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ROLE")
public class Role extends BaseEntity implements Serializable {

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IS_ACTIVE", columnDefinition = "boolean default true", nullable = false)
    private Boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<RolePermission> rolePermissions;


    public Role(){}

    public Role(String name, String description, Boolean active) {
        this.name = name;
        this.description = description;
        this.active = active;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public Role(RoleWrapper roleWrapper) {
        this.name = roleWrapper.getName();
        this.active = roleWrapper.isActive();
        this.description = roleWrapper.getDescription();
    }
}
