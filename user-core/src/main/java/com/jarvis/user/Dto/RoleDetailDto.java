package com.jarvis.user.dto;

import com.jarvis.user.entity.UserRole;
import com.jarvis.user.enums.OrgType;

import java.util.List;

/**
 * 角色详情
 * Created by ZZF on 2018/3/26.
 */
public class RoleDetailDto extends UserRole{
    /**
     * 集团id
     */
    private Long groupId;

    /**
     * 集团名称
     */
    private String groupName;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 机构类型
     */
    private OrgType orgType;
    /**
     * 功能列表
     */
    List<ModuleFunctionDto> moduleFunctionDtoList;

    public List<ModuleFunctionDto> getModuleFunctionDtoList() {
        return moduleFunctionDtoList;
    }

    public void setModuleFunctionDtoList(List<ModuleFunctionDto> moduleFunctionDtoList) {
        this.moduleFunctionDtoList = moduleFunctionDtoList;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public OrgType getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }
}

