package com.jarvis.user.entity;

import com.jarvis.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by jian on 18-3-9
 */
@Entity
@ApiModel
public class FunctionPoint extends BaseEntity {
    /**
     * 模块Id
     */
    @ApiModelProperty("模块Id")
    @Column(length = 20, nullable = false)
    private Long moduleId;
    /**
     * 功能名称
     */
    @ApiModelProperty("功能名称")
    @Column(length = 40, nullable = false)
    private String functionName;
    /**
     * 功能编号
     */
    @ApiModelProperty("功能编号")
    @Column(length = 40, nullable = false)
    private String functionCode;
    /**
     * 是否有效
     */
    @ApiModelProperty("是否有效")
    @Type(type = "yes_no")
    @Column(nullable = false)
    private Boolean enabled;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
