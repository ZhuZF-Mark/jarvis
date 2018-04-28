package com.jarvis.user.dto;

/**
 * 老表用户角色映射类
 */
public class UserRoleDto {
    private String userId;
    private String roleName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}