package com.jarvis.user.entity;

import com.jarvis.base.entity.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by jian on 18-3-9
 */
@Entity
public class User extends BaseEntity {
    /**
     * 用户名
     */
    @Column(length = 40, nullable = false)
    private String username;
    /**
     * 真实姓名
     */
    @Column(length = 40, nullable = false)
    private String realName;
    /**
     * 手机号
     */
    @Column(length = 20, nullable = false)
    private String mobile;
    /**
     * 密码
     */
    @Column(length = 100)
    private String password;
    /**
     * 是否有效
     */
    @Type(type = "yes_no")
    @Column(nullable = false)
    private Boolean enabled;
    /**
     * 备注
     */
    @Column
    private String memo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
