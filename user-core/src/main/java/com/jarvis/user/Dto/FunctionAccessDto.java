package com.jarvis.user.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 用户拥有某某块下的所有功能权限列表
 * Created by ZZF on 2018/3/22.
 */
@ApiModel
public class FunctionAccessDto implements Serializable {
    /**
     * 功能点code
     */
    @ApiModelProperty("功能点code")
    private String functionCode;
    /**
     * 功能点名称
     */
    @ApiModelProperty("功能点名称")
    private String functionName;
    /**
     * 权限表达式列表
     */
    @ApiModelProperty("权限表达式列表")
    private List<String> accessExpr;

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public List<String> getAccessExpr() {
        return accessExpr;
    }

    public void setAccessExpr(List<String> accessExpr) {
        this.accessExpr = accessExpr;
    }
}
