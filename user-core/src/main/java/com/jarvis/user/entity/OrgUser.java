package com.jarvis.user.entity;

import com.jarvis.base.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by ZZF on 2018/3/14.
 */
@Entity
public class OrgUser extends BaseEntity {
    /**
     * 用户id
     */
    @Column(length =20,nullable = false)
    private long userId;
    /**
     * 机构关联ID
     */
    @Column(length =20,nullable = false)
    private long orgRefId;
    /**
     * 是否默认机构
     */
    @Column(nullable = false)
    @Type(type = "yes_no")
    private Boolean defaultOrg;
    /**
     * 是否有效
     */
    @Type(type = "yes_no")
    @Column(nullable = false)
    private Boolean enabled;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrgRefId() {
        return orgRefId;
    }

    public void setOrgRefId(long orgRefId) {
        this.orgRefId = orgRefId;
    }

    public Boolean getDefaultOrg() {
        return defaultOrg;
    }

    public void setDefaultOrg(Boolean defaultOrg) {
        this.defaultOrg = defaultOrg;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
