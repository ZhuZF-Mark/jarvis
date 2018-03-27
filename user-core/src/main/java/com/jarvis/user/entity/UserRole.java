package com.jarvis.user.entity;

import com.jarvis.base.entity.BaseEntity;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by jian on 18-3-9
 */
@Entity
public class UserRole extends BaseEntity {

    /**
     * 角色名
     */
    @Column(length = 20,nullable = false)
    private String name;

    /**
     * 是否全局角色
     */
    @Column(nullable = false)
    @Type(type = "yes_no")
    private Boolean globalRole;

    /**
     * 关联机构id
     */
    private Long orgRefId;

    /**
     * 是否有效
     */
    @Column(nullable = false)
    @Type(type = "yes_no")
    private Boolean enabled;

    /**
     * 备注
     */
    private String memo;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGlobalRole() {
        return globalRole;
    }

    public void setGlobalRole(Boolean globalRole) {
        this.globalRole = globalRole;
    }

    public Long getOrgRefId() {
        return orgRefId;
    }

    public void setOrgRefId(Long orgRefId) {
        this.orgRefId = orgRefId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
