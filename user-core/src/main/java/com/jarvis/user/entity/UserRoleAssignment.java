package com.jarvis.user.entity;

import com.jarvis.base.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by jian on 18-3-9
 */
@Entity
public class UserRoleAssignment extends BaseEntity {
    /**
     * 用户Id
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * 机构用户Id
     */
    @Column(nullable = false)
    private Long orgUserId;

    /**
     * 角色id
     */
    @Column(nullable = false)
    private Long roleId;

    /**
     * 关联机构id
     */
    @Column(nullable = false)
    private Long orgRefId;

    /**
     * 开始日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDate;

    /**
     * 结束日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    /**
     * 是否有效
     */
    @Column(nullable = false)
    @Type(type = "yes_no")
    private Boolean enabled;

    /**
     * 是否长期有效
     */
    @Column(nullable = false)
    @Type(type = "yes_no")
    private Boolean unbound;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrgUserId() {
        return orgUserId;
    }

    public void setOrgUserId(Long orgUserId) {
        this.orgUserId = orgUserId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getOrgRefId() {
        return orgRefId;
    }

    public void setOrgRefId(Long orgRefId) {
        this.orgRefId = orgRefId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getUnbound() {
        return unbound;
    }

    public void setUnbound(Boolean unbound) {
        this.unbound = unbound;
    }
}
