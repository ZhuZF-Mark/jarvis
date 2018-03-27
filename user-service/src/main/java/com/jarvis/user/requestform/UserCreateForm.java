package com.jarvis.user.requestform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.List;

/**
 *
 * Created by ZZF on 2018/3/16.
 */
@ApiModel
public class UserCreateForm {
    @ApiModelProperty("用户id")
    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("备注")
    private String memo;
    @ApiModelProperty("默认机构Id")
    private Long defaultOrgId;
    @ApiModelProperty("关联机构")
    private List<OrganizationReferenceForm> organizationReferenceFormList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDefaultOrgId() {
        return defaultOrgId;
    }

    public void setDefaultOrgId(Long defaultOrgId) {
        this.defaultOrgId = defaultOrgId;
    }

    public List<OrganizationReferenceForm> getOrganizationReferenceFormList() {
        return organizationReferenceFormList;
    }

    public void setOrganizationReferenceFormList(List<OrganizationReferenceForm> organizationReferenceFormList) {
        this.organizationReferenceFormList = organizationReferenceFormList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "UserCreateForm{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", memo='" + memo + '\'' +
                ", defaultOrgId=" + defaultOrgId +
                ", organizationReferenceFormList=" + organizationReferenceFormList +
                '}';
    }
}
