package com.zainimtiaz.nagarro.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="USER_PERMISSION")
public class UserPermission extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERMISSION_ID", nullable = false)
    private Permission permission;

    public UserPermission() {
    }

    public UserPermission(Permission permission, User user) {
        this.setPermission(permission);
        this.setUser(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
