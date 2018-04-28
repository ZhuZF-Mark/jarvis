package com.jarvis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户列表关联机构信息类
 * @author ZZF
 */
@ApiModel
public class OrgRefDto {
    @ApiModelProperty("主键id")
    private Long orgRefId;
    @ApiModelProperty("集团id")
    private Long groupId;
    @ApiModelProperty("集团名")
    private String groupName;
    @ApiModelProperty("机构id")
    private Long orgId;
    @ApiModelProperty("机构名")
    private String orgName;

    public Long getOrgRefId() {
        return orgRefId;
    }

    public void setOrgRefId(Long orgRefId) {
        this.orgRefId = orgRefId;
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
}