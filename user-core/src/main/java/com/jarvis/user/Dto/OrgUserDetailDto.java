package com.jarvis.user.dto;

import com.jarvis.user.enums.OrgType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * Created by ZZF on 2018/3/15.
 */
@ApiModel
public class OrgUserDetailDto {
    /**
     * 集团机构关联关系id
     */
    @ApiModelProperty("集团机构关联关系id")
    private Long id;
    /**
     * orgUserId
     */
    @ApiModelProperty("orgUserId")
    private Long orgUserId;

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
     * 是否默认机构
     */
    private Boolean defaultOrg;
    /**
     * 机构类型
     */
    @ApiModelProperty("机构类型")
    private OrgType orgType;

    public OrgType getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }

    public Long getOrgUserId() {
        return orgUserId;
    }

    public void setOrgUserId(Long orgUserId) {
        this.orgUserId = orgUserId;
    }

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

    public Boolean getDefaultOrg() {
        return defaultOrg;
    }

    public void setDefaultOrg(Boolean defaultOrg) {
        this.defaultOrg = defaultOrg;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
