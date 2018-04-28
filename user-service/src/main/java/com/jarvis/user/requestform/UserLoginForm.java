package com.jarvis.user.requestform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户登陆提交表单
 * Created by ZZF on 2018/3/13.
 */
@ApiModel
public class UserLoginForm implements Serializable{
    private static final long serialVersionUID =1L;
    @ApiModelProperty("手机号")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("模块号")
    private String moduleCode;
    @ApiModelProperty("回调业务系统地址")
    private String callbackUrl;

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

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
