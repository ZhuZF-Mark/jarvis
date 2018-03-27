package com.jarvis.user.requestform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by ZZF on 2018/3/15.
 */
@ApiModel
public class RoleForm {
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
     * 备注
     */
    @ApiModelProperty("备注")
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
