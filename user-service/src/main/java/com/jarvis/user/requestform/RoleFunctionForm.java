package com.jarvis.user.requestform;

import com.jarvis.user.Dto.FunctionPointDto;
import com.jarvis.user.entity.FunctionPoint;
import com.jarvis.user.entity.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 角色添加功能权限Form表单
 * Created by ZZF on 2018/3/21.
 */
@ApiModel
public class RoleFunctionForm {
    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private UserRole role;
    /**
     * 功能列表
     */
    @ApiModelProperty("功能列表")
    private List<FunctionPointDto> functionPointList;

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<FunctionPointDto> getFunctionPointList() {
        return functionPointList;
    }

    public void setFunctionPointList(List<FunctionPointDto> functionPointList) {
        this.functionPointList = functionPointList;
    }
}
