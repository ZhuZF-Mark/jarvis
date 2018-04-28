package com.jarvis.user.dto;

import com.jarvis.user.entity.SystemModule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 模块下功能列表
 * Created by ZZF on 2018/3/20.
 */
@ApiModel
public class ModuleFunctionDto extends SystemModule {
    /**
     * 功能列表
     */
    @ApiModelProperty("功能列表")
    private List<FunctionPointDto> functionPointList;

    public List<FunctionPointDto> getFunctionPointList() {
        return functionPointList;
    }

    public void setFunctionPointList(List<FunctionPointDto> functionPointList) {
        this.functionPointList = functionPointList;
    }
}
