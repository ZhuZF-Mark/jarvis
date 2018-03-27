package com.jarvis.user.requestform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户登陆提交表单
 * Created by ZZF on 2018/3/13.
 */
@ApiModel
public class UserLoginForm {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("模块号")
    private String moduleCode;
    @ApiModelProperty("当前url")
    private String url;
    @ApiModelProperty("机构关联id")
    private Long orgRefId;

    public Long getOrgRefId() {
        return orgRefId;
    }

    public void setOrgRefId(Long orgRefId) {
        this.orgRefId = orgRefId;
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

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
