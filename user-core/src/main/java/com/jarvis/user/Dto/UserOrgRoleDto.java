package com.jarvis.user.Dto;

import com.jarvis.user.enums.OrgType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 *
 * Created by ZZF on 2018/3/19.
 */
@ApiModel
public class UserOrgRoleDto {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;
    /**
     * 机构id
     */
    @ApiModelProperty("机构id")
    private Long orgId;
    /**
     * 机构名称
     */
    @ApiModelProperty("机构名称")
    private String orgName;
    /**
     * 机构类型
     */
    @ApiModelProperty("机构类型")
    private OrgType orgType;
    /**
     * 角色Id
     */
    @ApiModelProperty("角色Id")
    private Long roleId;
    /**
     * 角色名
     */
    @ApiModelProperty("角色名")
    private String roleName;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Date fromDate;
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Date endDate;
    /**
     * 是否永久有效
     */
    @ApiModelProperty("是否永久有效")
    private Boolean unbound;

    public OrgType getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
