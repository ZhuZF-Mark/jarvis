package com.jarvis.user.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色列表查询条件
 * Created by ZZF on 2018/3/20.
 */
@ApiModel
public class RoleListQuery {
    @ApiModelProperty("机构集团关联Id")
    private Long orgRefId;
    @ApiModelProperty("是否全局角色")
    private Boolean globalRole;
    @ApiModelProperty("角色名")
    private String name;
    @ApiModelProperty("开始条数")
    private Integer pageNo;
    @ApiModelProperty("一页大小")
    private Integer pageSize;

    public Long getOrgRefId() {
        return orgRefId;
    }

    public void setOrgRefId(Long orgRefId) {
        this.orgRefId = orgRefId;
    }

    public Boolean getGlobalRole() {
        return globalRole;
    }

    public void setGlobalRole(Boolean globalRole) {
        this.globalRole = globalRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
