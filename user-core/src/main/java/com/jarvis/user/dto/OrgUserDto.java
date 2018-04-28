package com.jarvis.user.dto;

import com.jarvis.user.entity.OrganizationReference;
import com.jarvis.user.entity.User;

/**
 * 机构用户
 * Created by ZZF on 2018/4/24.
 */
public class OrgUserDto extends User {
    /**
     * 关联机构信息
     */
    private OrganizationReference organizationReference;

    public OrganizationReference getOrganizationReference() {
        return organizationReference;
    }

    public void setOrganizationReference(OrganizationReference organizationReference) {
        this.organizationReference = organizationReference;
    }
}
