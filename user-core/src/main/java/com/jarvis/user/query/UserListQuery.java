package com.jarvis.user.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by ZZF on 2018/3/19.
 */
@ApiModel
public class UserListQuery {
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("关联机构id")
    private Long orgRefId;
    @ApiModelProperty("页码")
    private Integer pageNo=0;
    @ApiModelProperty("一页条数")
    private Integer pageSize=10;

    public Long getOrgRefId() {
        return orgRefId;
    }

    public void setOrgRefId(Long orgRefId) {
        this.orgRefId = orgRefId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
