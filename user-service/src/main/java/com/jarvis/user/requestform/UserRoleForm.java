package com.jarvis.user.requestform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by ZZF on 2018/3/16.
 */
@ApiModel
public class UserRoleForm {
    /**
     * id
     */
    @ApiModelProperty("角色Id")
    private Long id;
    /**
     * 角色名
     */
    @ApiModelProperty("角色名")
    private String name;

    /**
     * 是否全局角色
     */
    @ApiModelProperty("是否全局角色")
    private Boolean globalRole;

    /**
     * 关联机构id
     */
    @ApiModelProperty("关联机构id")
    private Long orgRefId;

    /**
     * 是否有效
     */
    @ApiModelProperty("是否有效")
    private Boolean enabled;
    /**
     * 开始日期
     */
    @ApiModelProperty("开始日期")
    private Date fromDate;

    /**
     * 结束日期
     */
    @ApiModelProperty("结束日期")
    private Date endDate;
    /**
     * 是否长期有效
     */
    @ApiModelProperty("是否长期有效")
    private Boolean unbound;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;
    /**
     * 是否选中
     */
    @ApiModelProperty("是否选中")
    private Boolean checked=false;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getUnbound() {
        return unbound;
    }

    public void setUnbound(Boolean unbound) {
        this.unbound = unbound;
    }

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
}
