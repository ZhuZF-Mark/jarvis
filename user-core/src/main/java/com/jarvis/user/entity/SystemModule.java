package com.jarvis.user.entity;


import com.jarvis.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by ZZF on 2018/3/12.
 */
@Entity
@ApiModel
public class SystemModule extends BaseEntity {
    /**
     * 模块名
     */
    @ApiModelProperty("模块名")
    @Column(length = 40, nullable = false)
    private String moduleName;
    /**
     * 模块code
     */
    @ApiModelProperty("模块code")
    @Column(length = 40, nullable = false)
    private String moduleCode;
    /**
     * 回掉地址
     */
    @ApiModelProperty("回掉地址")
    @Column(length = 255, nullable = false)
    private String callbackUrl;
    /**
     * 是否有效
     */
    @ApiModelProperty("是否有效")
    @Type(type = "yes_no")
    @Column(nullable = false)
    private Boolean enabled;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @Column
    private String memo;

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
