package com.jarvis.user.requestform;

import com.jarvis.user.enums.OrgType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 *
 * Created by ZZF on 2018/3/16.
 */
@ApiModel
public class OrganizationReferenceForm {
    /**
     * id
     */
    private Long id;
    /**
     * 集团id
     */
    @ApiModelProperty("集团id")
    private Long groupId;

    /**
     * 集团名称
     */
    @ApiModelProperty("集团名称")
    private String groupName;

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
     * 角色list
     */
    @ApiModelProperty("角色list")
    private List<UserRoleForm> userRoleFormList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public OrgType getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }

    public List<UserRoleForm> getUserRoleFormList() {
        return userRoleFormList;
    }

    public void setUserRoleFormList(List<UserRoleForm> userRoleFormList) {
        this.userRoleFormList = userRoleFormList;
    }
}
