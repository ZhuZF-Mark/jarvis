package com.jarvis.user.entity;

import com.jarvis.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 角色模块关联表
 * Created by ZZF on 2018/3/20.
 */
@Entity
public class FunctionAccess extends BaseEntity{
    /**
     * 角色id
    */
    @Column(length = 20,nullable =false)
    private Long roleId;
    /**
     * 模块id
     */
    @Column(length = 20,nullable =false)
    private Long moduleId;
    /**
     * 功能id
     */
    @Column(length = 20,nullable =false)
    private Long functionId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }
}
