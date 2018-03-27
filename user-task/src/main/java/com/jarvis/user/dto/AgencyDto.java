package com.jarvis.user.dto;

/**
 * Created by Dong yangjian on 2018/3/14.
 */
public class AgencyDto {

    private Long id;

    private Long parentId;

    private Long groupId;

    private String agencyCode;

    private String agencyName;

    private String agencyShortName;

    private Boolean useFlg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyShortName() {
        return agencyShortName;
    }

    public void setAgencyShortName(String agencyShortName) {
        this.agencyShortName = agencyShortName;
    }

    public Boolean getUseFlg() {
        return useFlg;
    }

    public void setUseFlg(Boolean useFlg) {
        this.useFlg = useFlg;
    }
}
