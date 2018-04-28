package com.jarvis.user.requestform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 初始化功能表单
 * Created by ZZF on 2018/3/28.
 */
@ApiModel
public class FunctionInitForm {
    /**
     * 功能名称
     */
    @ApiModelProperty("功能名称")
    private String functionName;
    /**
     * 功能编号
     */
    @ApiModelProperty("功能编号")
    private String functionCode;
    /**
     * 权限列表
     */
    @ApiModelProperty("权限列表")
    List<String> dataAccess;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public List<String> getDataAccess() {
        return dataAccess;
    }

    public void setDataAccess(List<String> dataAccess) {
        this.dataAccess = dataAccess;
    }
}
