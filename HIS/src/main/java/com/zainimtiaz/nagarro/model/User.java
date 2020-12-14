package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/*
 * @author    : Tahir Mehmood
 * @Date      : 18-Jul-2018
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
 * @FileName  : User
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "USER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "USER_TYPE", nullable = false)
    private String userType;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "IS_ACTIVE", columnDefinition = "boolean default false", nullable = false)
    private Boolean active;

    @JsonIgnore
    @OneToMany(targetEntity = UserRole.class, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserRole> userRoles;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserPermission> userPermissionList;

    public User(){}
    public User(String username, String userType, String password, Boolean active) {
        this.username = username;
        this.userType = userType;
        this.password = password;
        this.active = active;
        this.userRoles = userRoles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<UserPermission> getUserPermissionList() {
        return userPermissionList;
    }

    public void setUserPermissionList(List<UserPermission> userPermissionList) {
        this.userPermissionList = userPermissionList;
    }
}
