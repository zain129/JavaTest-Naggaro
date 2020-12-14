package com.zainimtiaz.nagarro.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zainimtiaz.nagarro.model.Permission;
import com.zainimtiaz.nagarro.model.RolePermission;

/*
 * @author    : waqas kamran
 * @Date      : 17-Apr-18
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
 * @Package   : com.sd.his.wrapper
 * @FileName  : PermissionWrapper
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionWrapper {
    long id;
    String name;
    String description;
    boolean deleted;
    boolean active;

    //url for routing on front end
    String routeUrl;
    //permission actions according to role
    boolean canAdd;
    boolean candEdit;
    boolean canDelete;
    String  permissionIcon;
    String  label;
    char indicatior;

    public PermissionWrapper() {
    }

    public PermissionWrapper(Permission permission) {
        this.id = permission.getId();
        this.name = permission.getName();
        this.description = permission.getDescription();
        this.active = permission.getActive();
        this.label =permission.getName();
    }

    public PermissionWrapper(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public PermissionWrapper(long id, String name, String description, boolean deleted, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deleted = deleted;
        this.active = active;
    }

    public PermissionWrapper(RolePermission rolePermission) {
        this.id = rolePermission.getPermission().getId();
        this.name = rolePermission.getPermission().getName();
        this.description = rolePermission.getPermission().getDescription();
        this.permissionIcon = rolePermission.getPermission().getPermissionIcon();
        this.active = rolePermission.getPermission().getActive();
        this.canAdd = rolePermission.getCreate();
        this.candEdit = rolePermission.getUpdate();
        this.canDelete = rolePermission.getDelete();
        this.routeUrl = rolePermission.getPermission().getUrl();
        this.label =rolePermission.getPermission().getName();
        this.indicatior = rolePermission.getPermission().getPermissionForInd();
    }

    public char getIndicatior() {
        return indicatior;
    }

    public void setIndicatior(char indicatior) {
        this.indicatior = indicatior;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }

    public boolean isCanAdd() {
        return canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }

    public boolean isCandEdit() {
        return candEdit;
    }

    public void setCandEdit(boolean candEdit) {
        this.candEdit = candEdit;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public String getPermissionIcon() {
        return permissionIcon;
    }

    public void setPermissionIcon(String permissionIcon) {
        this.permissionIcon = permissionIcon;
    }
}