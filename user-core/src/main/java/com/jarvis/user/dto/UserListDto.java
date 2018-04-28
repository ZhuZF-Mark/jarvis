package com.jarvis.user.dto;

import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.List;

/**
 * 用户列表展现类
 * @author ZZF
 */
public class UserListDto {
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否有效
     */
    @Type(type = "yes_no")
    private Boolean enabled;
    private Date createdTime;
    private Date updatedTime;
    /**
     * 备注
     */
    private String memo;
    /**
     * 机构信息
     */
    private List<OrgRefDto> orgRefDtoList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<OrgRefDto> getOrgRefDtoList() {
        return orgRefDtoList;
    }

    public void setOrgRefDtoList(List<OrgRefDto> orgRefDtoList) {
        this.orgRefDtoList = orgRefDtoList;
    }
}