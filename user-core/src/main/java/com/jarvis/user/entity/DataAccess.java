package com.jarvis.user.entity;

import com.jarvis.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by jian on 18-3-9
 */
@Entity
public class DataAccess extends BaseEntity {
    /**
     * 功能Id
     */
    @Column(length = 20, nullable = false)
    private Long functionId;
    /**
     * 权限表达式
     */
    @Column(length = 50, nullable = false)
    private String accessExpr;

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public String getAccessExpr() {
        return accessExpr;
    }

    public void setAccessExpr(String accessExpr) {
        this.accessExpr = accessExpr;
    }
}
