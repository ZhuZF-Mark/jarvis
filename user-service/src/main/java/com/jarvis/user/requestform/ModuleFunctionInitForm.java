package com.jarvis.user.requestform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 初始化模块功能权限表单
 * Created by ZZF on 2018/3/28.
 */
@ApiModel
public class ModuleFunctionInitForm {
    /**
     * 模块名
     */
    @ApiModelProperty("模块名")
    private String moduleName;
    /**
     * 模块code
     */
    @ApiModelProperty("模块code")
    private String moduleCode;
    /**
     * 回掉地址
     */
    @ApiModelProperty("回掉地址")
    private String callbackUrl;
    /**
     * 功能列表
     */
    @ApiModelProperty("功能列表")
    List<FunctionInitForm> functionInitFormList;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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

    public List<FunctionInitForm> getFunctionInitFormList() {
        return functionInitFormList;
    }

    public void setFunctionInitFormList(List<FunctionInitForm> functionInitFormList) {
        this.functionInitFormList = functionInitFormList;
    }
}
