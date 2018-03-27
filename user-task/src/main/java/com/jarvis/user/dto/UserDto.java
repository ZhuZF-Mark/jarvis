package com.jarvis.user.dto;

import java.util.Date;

/**
 * Created by Dong yangjian on 2018/3/14.
 */
public class UserDto {

    private Long agencyId;

    private Long suplierId;

    private String userId;

    private String userPwd;

    private String trueName;

    private String mobile;

    private Boolean useFlg;

    private String memo;

    private String creator;

    private String editor;

    private Date createdTime;

    private Date updatedTime;

    private Long defaultAgency;

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public Long getSuplierId() {
        return suplierId;
    }

    public void setSuplierId(Long suplierId) {
        this.suplierId = suplierId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getUseFlg() {
        return useFlg;
    }

    public void setUseFlg(Boolean useFlg) {
        this.useFlg = useFlg;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
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

    public Long getDefaultAgency() {
        return defaultAgency;
    }

    public void setDefaultAgency(Long defaultAgency) {
        this.defaultAgency = defaultAgency;
    }
}
