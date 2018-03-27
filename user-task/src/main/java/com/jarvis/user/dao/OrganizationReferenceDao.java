package com.jarvis.user.dao;

import com.jarvis.user.entity.OrganizationReference;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Dong yangjian on 2018/3/14.
 */
public interface OrganizationReferenceDao extends JpaRepository<OrganizationReference, Long> {
    OrganizationReference findByOrgId(Long agencyId);
}
