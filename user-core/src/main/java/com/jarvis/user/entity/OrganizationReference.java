package com.jarvis.user.entity;

import com.jarvis.base.entity.BaseEntity;
import com.jarvis.user.enums.OrgType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by Dong yangjian on 2018/3/14.
 */
@Entity
public class OrganizationReference extends BaseEntity {

    /**
     * 集团id
     */
    @Column(nullable = false)
    private Long groupId;

    /**
     * 集团名称
     */
    @Column(length = 60, nullable = false)
    private String groupName;

    /**
     * 机构id
     */
    @Column(nullable = false)
    private Long orgId;

    /**
     * 机构名称
     */
    @Column(length = 60, nullable = false)
    private String orgName;

    /**
     * 机构类型
     */
    @Column(length = 20, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrgType orgType;

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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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
