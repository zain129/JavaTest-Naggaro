package com.zainimtiaz.nagarro.wrapper.request;

import java.util.List;

public class UserPermissionRequestWrapper {
    private List<Long> permissionIds;
    private Long userId;

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
